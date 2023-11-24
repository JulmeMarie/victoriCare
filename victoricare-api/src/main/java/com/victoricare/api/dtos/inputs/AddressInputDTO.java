package com.victoricare.api.dtos.inputs;

import java.io.Serializable;
import lombok.Data;

@Data
public class AddressInputDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String street;

    private String addtionnal;

    private String zipCode;

    private String town;

    private String country;

    private Integer creationAuthorId;
}
