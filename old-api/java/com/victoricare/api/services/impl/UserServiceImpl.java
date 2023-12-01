package com.victoricare.api.services.impl;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.victoricare.api.dtos.PasswordDTO;
import com.victoricare.api.dtos.UserDTO;
import com.victoricare.api.dtos.ValidateDTO;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.EFolder;
import com.victoricare.api.enums.ERight;
import com.victoricare.api.enums.ERole;
import com.victoricare.api.exceptions.FormException;
import com.victoricare.api.exceptions.ResourceAlreadyExistsException;
import com.victoricare.api.exceptions.ResourceNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.exceptions.RightException;
import com.victoricare.api.exceptions.UnknownException;
import com.victoricare.api.models.UserModel;
import com.victoricare.api.repositories.UserRepository;
import com.victoricare.api.security.jwt.JWTUtils;
import com.victoricare.api.services.IFileService;
import com.victoricare.api.services.IUserMailService;
import com.victoricare.api.services.IUserService;
import com.victoricare.api.utils.RandomInt;

@Service
public class UserServiceImpl implements IUserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	public UserRepository userRepository;

	@Autowired
	private IUserMailService emailService;

	@Autowired
	private IFileService fileService;
	
	@Autowired
	private JWTUtils jwtUtils;
	

	private final static String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
	private final static String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9]{6,}$";

	@Override
	public Optional<User> findNotDeletedByIdentifier(String username) {
		return this.userRepository.findNotDeletedByIdentifier(username);
	}
	@Override
	public UserModel update(User author, PasswordEncoder encoder, UserDTO appRequest) {

		User user;
		if( appRequest.getId() != author.getIdUser()) {
			//Then he should be an administrator
			if(!ERight.isAtLeastAdministrator(author.getRightUser())) {
				logger.error("Authenticated user doesn't have the right to update other user : {}", author.getIdUser());
				throw new RightException();
			}
			user = this.userRepository.findById(appRequest.getId()).get();
		}
		else {
			user = author;
		}
			//Then another user should not exist with the same username
		this.userRepository.findByUsernameUser(appRequest.getUsername()).ifPresent(
			usr -> {
				if(!usr.getIdUser().equals(user.getIdUser())) {
					logger.error("user already exist with username : {}", user.getIdUser());
					throw new ResourceNotFoundException();
				}
			 }
		 );

		 user.setUpdateAtUser(Date.from(Instant.now()));
		 user.setUpdateByUser(author);
		 user.setDescriptionUser(appRequest.getDescription());
		 user.setLastnameUser(appRequest.getLastname());
		 user.setFirstnameUser(appRequest.getFirstname());

		 if(!appRequest.getMFile().isEmpty()) {
			this.fileService.setS3BucketName(this.jwtUtils.getS3BucketName());
			final String fileToDelete = user.getPhotoUser();
			String filename = fileService.save(EFolder.USER,appRequest.getMFile());
			user.setPhotoUser(filename);
			fileService.delete(fileToDelete);
		}

		 if(ERight.isAtLeastAdministrator(author.getRightUser())) {
			user.setContactAddresseeUser(appRequest.isContactAddressee());
			user.setRightUser(ERight.get(appRequest.getRight()).name());
			user.setRoleUser(ERole.get(appRequest.getRole()).name());
		 }
		 this.userRepository.save(user);

		 if(author.getIdUser() != user.getIdUser()) {
		   this.emailService.mailForAccountUpdate(user);
		 }
		 fileService.setS3BucketUrl(this.jwtUtils.getS3BucketUrl());
		 return UserModel.newInstance().init(fileService ,user);
	}

	@Override
	public UserModel uploadImage(User onlineUser, MultipartFile mFile) {
		if(!mFile.isEmpty()) {
			this.fileService.setS3BucketName(this.jwtUtils.getS3BucketName());
			final String fileToDelete = onlineUser.getPhotoUser();
			String filename = fileService.save(EFolder.USER,mFile);
			onlineUser.setPhotoUser(filename);
		    onlineUser.setUpdateAtUser(Date.from(Instant.now()));
		    this.userRepository.save(onlineUser);
			fileService.delete(fileToDelete);
		}
		fileService.setS3BucketUrl(this.jwtUtils.getS3BucketUrl());
		return UserModel.newInstance().init(fileService ,onlineUser);
	}

	@Override
	public void signUp(User author, UserDTO appRequest) {

		Date now = Date.from(Instant.now());
		//Only administrator can create a user, so let's check that
		if(!ERight.isAtLeastAdministrator(author.getRightUser())) {
			logger.error("Authenticated user doesn't have the right to create a user : {}", author.getIdUser());
			throw new RightException();
		}

		User user = this.userRepository.findByEmailUser(appRequest.getEmail()).orElse(null);

		if(user != null){
			if(user.getAccountValidateAtUser() != null) {
				logger.error("user already exist with email : {}", appRequest.getEmail());
				throw new ResourceNotFoundException();
			}
			else if(user.getCreateAtUser().after(Date.from(Instant.now().minusSeconds(48*60*60)))) {
				logger.error("can not create same user for now : {}", appRequest.getEmail());
				throw new ResourceNotFoundException();
			}
		}

		try {
			if(user == null) {
				user = new User();
			}
			user.setAccountCodeUser(RandomInt.get6DigitCode());
			user.setRoleUser(ERole.get(appRequest.getRole()).name());
			user.setRightUser(ERight.get(appRequest.getRight()).name());
			user.setCreateAtUser(now);
			user.setCreateByUser(author);
			user.setEmailUser(appRequest.getEmail());
			user.setPhotoUser(FileServiceImpl.AVATAR);
			user.setDescriptionUser(appRequest.getDescription());
			user.setFirstnameUser(appRequest.getFirstname());
			user.setLastnameUser(appRequest.getLastname());
			this.userRepository.save(user);
			this.emailService.mailForAccountCreation(user);
		} catch (Exception e) {
			logger.error("an error has occured {}", e);
			throw new UnknownException();
		}
	}

	@Override
	public void delete(User author, Integer userId) {

		if(!userId.equals(author.getIdUser())) {
			if(!ERight.isAtLeastAdministrator(author.getRightUser())) {
				logger.error("User doesn't have the right to delete : {}", author.getIdUser());
				throw new RightException();
			}
		}

		this.userRepository.findById(userId).ifPresentOrElse( user -> {
			user.setDeleteAtUser(Date.from(Instant.now()));
			user.setDeleteByUser(author);
			this.userRepository.save(user);
		},
		() -> {
			logger.error("user not found with id : {}", userId);
			throw new ResourceNotFoundException();
		});
	}

	@Override
	public UserModel updateEmail(User author, String email){
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		if(!matcher.matches()) {
			throw new FormException();
		}
		this.userRepository.findByEmailUser(email).ifPresentOrElse(user -> {
			logger.error("Can't update email for user : {} because {} already exists.", user.getIdUser(), email);
			throw new ResourceAlreadyExistsException();
		}, () -> {
			try {
				author.setEmailCodeUser(RandomInt.get6DigitCode());
				author.setEmailCodeAtUser(Date.from(Instant.now()));
				author.setEmailNewUser(email);
				this.emailService.mailForUpdateEmail(author);

				author.setEmailValidateAtUser(null);
				this.userRepository.save(author);
			}
			catch(Exception e) {
				throw new UnknownException();
			}
		});
		fileService.setS3BucketUrl(this.jwtUtils.getS3BucketUrl());
		return UserModel.newInstance().init(fileService ,author);
	}

	@Override
	public UserModel updatePassword(
			User author,
			PasswordEncoder encoder,
			PasswordDTO request) {

		Pattern pattern = Pattern.compile(passwordRegex);
		Matcher matcher = pattern.matcher(request.getPassword());
		if(!matcher.matches()) {
			throw new FormException();
		}

		Date now = Date.from(Instant.now());
		if(List.of("recovery", "account").contains(request.getType())) {
			if(!RandomInt.check6DigitCode(request.getCode())) {
				throw new FormException();
			}

			author = this.userRepository.findById(request.getId()).orElseThrow(()-> new ResourceNotFoundException());

			if(!author.getAccountCodeUser().equals(request.getCode())) {
				throw new ResourceNotAllowedException();
			}
			author.setPasswordUser(encoder.encode(request.getPassword()));
			author.setPasswordValidateAtUser(now);
			this.userRepository.save(author);
		}
		else if(author.getIdUser() > 0) {
			try {
				author.setPasswordNewUser(encoder.encode(request.getPassword()));
				author.setPasswordCodeAtUser(now);
				author.setPasswordCodeUser(RandomInt.get6DigitCode());
				author.setPasswordValidateAtUser(null);
				this.userRepository.save(author);
				this.emailService.mailForUpdatePassword(author);
			}
			catch(Exception e) {
				logger.error("Une erreur s'est produite : ", e);
				throw new UnknownException();
			}
		}
		
		fileService.setS3BucketUrl(this.jwtUtils.getS3BucketUrl());
		return UserModel.newInstance().init(fileService ,author);
	}

	@Override
	public UserModel updatePasswordWithCode(PasswordEncoder encoder, PasswordDTO request) {

		User author = this.userRepository.findById(request.getId()).orElseThrow(()-> new ResourceNotFoundException());
		if(author.getPasswordCodeUser() != request.getCode()) {
			throw new ResourceNotAllowedException();
		}

		Pattern pattern = Pattern.compile(passwordRegex);
		Matcher matcher = pattern.matcher(request.getPassword());
		if(!matcher.matches()) {
			throw new FormException();
		}
		try {
			author.setPasswordUser(encoder.encode(request.getPassword()));
			author.setPasswordValidateAtUser(Date.from(Instant.now()));
			this.userRepository.save(author);
			fileService.setS3BucketUrl(this.jwtUtils.getS3BucketUrl());
			return UserModel.newInstance().init(fileService ,author);
		}
		catch(Exception e) {
			logger.error("Une erreur s'est produite : ", e);
			throw new UnknownException();
		}
	}

	@Override
	public UserModel validate(ValidateDTO validateRequest) {
		if(!RandomInt.check6DigitCode(validateRequest.getCode())) {
			throw new FormException();
		}
		Date now = Date.from(Instant.now());
		User author = this.userRepository.findById(validateRequest.getId()).orElseThrow(()->new ResourceNotFoundException());

		if("update_email".equalsIgnoreCase(validateRequest.getType())) {
			if(validateRequest.getCode() != author.getEmailCodeUser()) {
				throw new FormException();
			}
			author.setEmailValidateAtUser(now);
		}
		else if("update_password".equalsIgnoreCase(validateRequest.getType().toLowerCase())) {
			if(validateRequest.getCode() != author.getPasswordCodeUser()) {
				throw new FormException();
			}
			author.setPasswordValidateAtUser(Date.from(Instant.now()));
		}
		else if( List.of("recovery", "account").contains(validateRequest.getType())) {
			if(!validateRequest.getCode().equals(author.getAccountCodeUser())){
				throw new FormException();
			}
			author.setAccountValidateAtUser(Date.from(Instant.now()));
		}
		else {
			throw new FormException();
		}

		this.userRepository.save(author);
		fileService.setS3BucketUrl(this.jwtUtils.getS3BucketUrl());
		return UserModel.newInstance().init(fileService ,author);
	}

	@Override
	public UserModel recover(String email) {
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		if(!matcher.matches()) {
			throw new FormException();
		}

		User author = this.userRepository.findByEmailUser(email).orElseThrow(()-> new ResourceNotFoundException());
		author.setAccountCodeUser(RandomInt.get6DigitCode());
		author.setAccountCodeAtUser(Date.from(Instant.now()));
		this.emailService.mailForRecoverAccount(author);
		this.userRepository.save(author);
		fileService.setS3BucketUrl(this.jwtUtils.getS3BucketUrl());
		return UserModel.newInstance().init(fileService ,author);
	}

	@Override
	public UserModel unique(User author, Integer userId) {
		User user = author;
		if(!userId.equals(author.getIdUser())) {
			if(!ERight.isAtLeastModerator(author.getRightUser())) {
				logger.error("User doesn't have the right to load photo : {}", author.getIdUser());
				throw new RightException();
			}
			user = this.userRepository.findById(userId).orElseThrow(() -> {
				logger.error("user not found : {}", userId);
				throw new ResourceNotFoundException();
			});
		}
		fileService.setS3BucketUrl(this.jwtUtils.getS3BucketUrl());
		return UserModel.newInstance().init(fileService ,user);
	}

	@Override
	public List<UserModel> list(ERole eRole) {
		List<String>roles = ERole.compute(eRole).stream().map(r->r.name()).collect(Collectors.toList());
		logger.info(roles.toString());
		fileService.setS3BucketUrl(this.jwtUtils.getS3BucketUrl());
		try {
			return this.userRepository.findAllUsers(roles).get().stream()
				.map(user-> UserModel.newInstance().init(fileService ,user))
				.collect(Collectors.toList());
		}
		catch(Exception e) {
			logger.error("An error has occured : {}", e);
			throw new ResourceNotFoundException();
		}
	}

	@Override
	public Optional<List<User>> findAllContactAddressees() {
		return this.userRepository.findAllContactAddressees();
	}
	@Override
	public boolean isContactAddressee(Integer userId) {
		Optional<User> user = this.userRepository.findById(userId);
		if(user.isPresent()) {
			return user.get().isContactAddresseeUser();
		}
		return false;
	}
}