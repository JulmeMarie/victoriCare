package com.victoricare.api.dtos.inputs;

import lombok.Data;
import java.util.Date;

@Data
public class PersonInputDTO {
    private Integer id;

    private String lastname;

    private String firstname;

    private String gender;

    private String phone;

    private String image;

    private String country;

    private Date birthday;

    private Integer creationAuthorId;

    private AddressInputDTO address;

    private Integer addressId;
}
