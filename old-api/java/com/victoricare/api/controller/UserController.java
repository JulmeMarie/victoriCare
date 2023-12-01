package com.victoricare.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.victoricare.api.dtos.PasswordDTO;
import com.victoricare.api.dtos.UserDTO;
import com.victoricare.api.dtos.ValidateDTO;
import com.victoricare.api.enums.ERole;
import com.victoricare.api.models.UserModel;
import com.victoricare.api.security.jwt.AuthTokenFilter;
import com.victoricare.api.services.IUserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@Validated
@RestController
public class UserController {

	@Autowired
	IUserService userService;

	@Autowired
	AuthenticationManager authenticationManager;

    @Autowired
	PasswordEncoder encoder;

	@PutMapping(path = "/api/right-adm/user", produces = MediaType.APPLICATION_JSON_VALUE, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
	public void signUp(Authentication authentication, @ModelAttribute UserDTO userRequest) {
		this.userService.signUp(AuthTokenFilter.getOnlineUser(authentication),userRequest);
	}

	@PostMapping(path = "/api/right-usr/user", produces = MediaType.APPLICATION_JSON_VALUE, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<UserModel> updateUser(Authentication authentication, @ModelAttribute UserDTO updateUserRequest) {
		return ResponseEntity.ok(this.userService.update(AuthTokenFilter.getOnlineUser(authentication), encoder, updateUserRequest));
	}

	@GetMapping(path = "/api/right-usr/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserModel> unique(Authentication authentication, @PathVariable("userId") Integer userId) {
		return ResponseEntity.ok(this.userService.unique(AuthTokenFilter.getOnlineUser(authentication) , userId));
	}

	@PostMapping(path = "/api/right-usr/update/email", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserModel> updateEmail(Authentication authentication, @RequestBody String email) {
		return ResponseEntity.ok(this.userService.updateEmail(AuthTokenFilter.getOnlineUser(authentication), email));
	}

	@PostMapping(path = "/api/right-anm/update/password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserModel> updatePassword(Authentication authentication, @RequestBody PasswordDTO passwordRequest) {
		return ResponseEntity.ok(this.userService.updatePassword(AuthTokenFilter.getOnlineUser(authentication), encoder, passwordRequest));
	}

	@PostMapping(path = "/api/right-anm/recover", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserModel> recover(@RequestBody String email) {
		return ResponseEntity.ok(this.userService.recover(email));
	}

	@PostMapping(path = "/api/right-anm/validation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserModel> validate(@RequestBody ValidateDTO validateRequest) {
		return ResponseEntity.ok(this.userService.validate(validateRequest));
	}

	@DeleteMapping(path = "/api/right-adm/user/{userId}")
	public void delete(Authentication authentication, @PathVariable("userId") Integer userId) {
		this.userService.delete(AuthTokenFilter.getOnlineUser(authentication) , userId);
	}

	@GetMapping(path = "/api/right-mod/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserModel>> findUsers() {
		return ResponseEntity.ok(this.userService.list(ERole.ALL));
	}

	@GetMapping(path = "/api/right-anm/teams", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserModel>> findTeam() {
		return ResponseEntity.ok(this.userService.list(ERole.TEAM));
	}

	@GetMapping(path = "/api/right-anm/members", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserModel>> findMembers() {
		return ResponseEntity.ok(this.userService.list(ERole.MEMBER));
	}

	@GetMapping(path = "/api/right-anm/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserModel>> findAllUsers() {
		return ResponseEntity.ok(this.userService.list(ERole.ALL));
	}

	@PostMapping(path = "/api/right-usr/user/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<UserModel> uploadImage(Authentication authentication, @RequestParam("mFile") MultipartFile mFile) {
		return ResponseEntity.ok(this.userService.uploadImage(AuthTokenFilter.getOnlineUser(authentication), mFile));
	}
}