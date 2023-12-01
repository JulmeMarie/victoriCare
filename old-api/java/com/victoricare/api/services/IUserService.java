package com.victoricare.api.services;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.victoricare.api.dtos.PasswordDTO;
import com.victoricare.api.dtos.UserDTO;
import com.victoricare.api.dtos.ValidateDTO;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.ERole;
import com.victoricare.api.models.UserModel;

public interface IUserService {

	public void signUp(User author,UserDTO userRequest);

	public UserModel update(User author, PasswordEncoder encoder, UserDTO userRequest);

	public void delete(User author, Integer userId);

	Optional<User> findNotDeletedByIdentifier(String username);

	public List<UserModel> list(ERole eRole);

	public Optional<List<User>> findAllContactAddressees();

	public UserModel unique(User author, Integer userId);


	public UserModel updatePassword(User author, PasswordEncoder encoder, PasswordDTO passwordRequest);

	public UserModel recover(String emailStr);

	public boolean isContactAddressee(Integer userId);

	public UserModel updateEmail(User onlineUser, String email);

	public UserModel validate(ValidateDTO validateRequest);

	public UserModel updatePasswordWithCode(PasswordEncoder encoder, PasswordDTO passwordRequest);

    public UserModel uploadImage(User onlineUser, MultipartFile mFile);

}
