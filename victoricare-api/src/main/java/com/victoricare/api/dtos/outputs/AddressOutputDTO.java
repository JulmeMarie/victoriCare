package com.victoricare.api.dtos.outputs;

import java.io.Serializable;
import lombok.Builder;

@Builder
public class AddressOutputDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String street;

    private String addtionnal;

    private String zipCode;

    private String town;

    private String country;
}
