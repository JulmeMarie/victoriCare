package com.victoricare.api.models;

import java.util.Date;
import org.springframework.web.multipart.MultipartFile;
import com.victoricare.api.entities.User;
import com.victoricare.api.services.IFileService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
private Integer id;

    private String email;

    private String username;

    private String firstname;

    private String lastname;

    private String description;

    private  String role;

    private  String right;

	private String urlFile;

	private MultipartFile mFile;

	private boolean isContactAddressee;

	private Date createAt;

	private Date deleteAt;

	private Date updateAt;

	private Date emailValidateAt;

	private Date accountValidateAt;

	private Date passwordValidateAt;

    private  Integer createBy;

    private  Integer deleteBy;

    private  Integer updateBy;

    public static UserModel newInstance() {
    	return new UserModel();
    }

    public UserModel init(IFileService fileService, User user) {
    	if(user == null ) {
    		return null;
    	}
		
    	this.id = user.getIdUser();
		this.username = user.getUsernameUser();
		this.firstname = user.getFirstnameUser();
		this.lastname = user.getLastnameUser();
		this.description = user.getDescriptionUser();
		this.email = user.getEmailUser();
		this.isContactAddressee = user.isContactAddresseeUser();
		this.role = user.getRoleUser();
		this.right = user.getRightUser();
		this.createAt = user.getCreateAtUser();
		this.deleteAt = user.getDeleteAtUser();
		this.updateAt = user.getUpdateAtUser();
		this.passwordValidateAt = user.getPasswordValidateAtUser();
		this.emailValidateAt = user.getEmailValidateAtUser();

        this.createBy = user.getCreateByUser() != null ? user.getCreateByUser().getIdUser() : null;
		this.deleteBy = user.getDeleteByUser() != null ? user.getDeleteByUser().getIdUser() : null;
		this.updateBy = user.getUpdateByUser() != null ? user.getUpdateByUser().getIdUser() : null;

		this.urlFile = user.getPhotoUser() != null ? fileService.getFileUrl(user.getPhotoUser()) : fileService.getAvatarFileUrl();
		return this;
    }
}
