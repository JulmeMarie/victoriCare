package com.victoricare.api.dtos.outputs;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
public class PersonOutputDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private String lastname;

    private String firstname;

    private String gender;

    private String phone;

    private String image;

    private String country;

    private Date creationDate;

    private Date updateDate;

    private Date deletionDate;

    private String roles;

    private Date birthday;

    private Integer updateAuthorId;

    private Integer deletionAuthorId;

    private Integer creationAuthorId;

    private AddressOutputDTO address;

    /*
     * public PersonOutputDTO(Person person) {
     * this.id = person.getId();
     * this.lastname = person.getLastname();
     * this.firstname = person.getFirstname();
     * this.gender = person.getGender();
     * this.phone = person.getPhone();
     * this.image = person.getImage();
     * this.country = person.getCountry();
     * this.creationDate = person.getCreationDate();
     * this.updateDate = person.getUpdateDate();
     * this.deletionDate = person.getDeletionDate();
     * this.roles = person.getRoles();
     * this.birthday = person.getBirthday();
     * this.updateAuthorId = person.getUpdateAuthorId();
     * this.deletionAuthorId = person.getDeletionAuthorId();
     * this.creationAuthorId = person.getCreationAuthorId();
     * this.address = new AddressOutputDTO(person.getAddress());
     * }
     */
}