package com.victoricare.api.dtos.outputs;

import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserOutputDTO extends PersonOutputDTO {

	private String pseudo;

	private String email;

	private Date banishmentDate;

	private Date creationDate;

	private Date updateDate;

	private Date deletionDate;

	private String rights;

	private String roles;

	private Integer signupId;

	private Integer banishmentAuthorId;

	private Integer deletionAuthorId;

	/*
	 * public UserOutputDTO(User user) {
	 * super(user);
	 * this.pseudo = user.getPseudo();
	 * this.email = user.getEmail();
	 * this.banishmentDate = user.getBanishmentDate();
	 * this.creationDate = user.getCreationDate();
	 * this.updateDate = user.getUpdateDate();
	 * this.deletionDate = user.getDeletionDate();
	 * this.rights = user.getRights();
	 * this.roles = user.getRoles();
	 * this.signupId = user.getSignUpId();
	 * this.banishmentAuthorId = user.getBanishmentAuthorId();
	 * this.deletionAuthorId = user.getDeletionAuthorId();
	 * }
	 */
}
