package com.victoricare.api.dtos.inputs;

import lombok.Data;

@Data
public class BabySitterInputDTO {

    private Long id;

    private String firstname;

    private String lastname;

    private Integer familyId;
}
