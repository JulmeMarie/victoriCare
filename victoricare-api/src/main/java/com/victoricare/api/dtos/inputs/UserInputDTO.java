package com.victoricare.api.dtos.inputs;

import com.victoricare.api.entities.Doc;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserInputDTO extends PersonInputDTO {

    private String pseudo;

    private String email;

    private String pass;

    private Doc description;
}
