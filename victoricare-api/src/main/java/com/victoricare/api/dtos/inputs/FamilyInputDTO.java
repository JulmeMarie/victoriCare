package com.victoricare.api.dtos.inputs;

import java.util.Set;
import lombok.Data;

@Data
public class FamilyInputDTO {

    private Integer id;

    private UserInputDTO parentOne;

    private UserInputDTO parentTwo;

    private UserInputDTO babySitter;

    private Set<ChildInputDTO> children;

    private Set<ChildInputDTO> attachedChildren;
}
