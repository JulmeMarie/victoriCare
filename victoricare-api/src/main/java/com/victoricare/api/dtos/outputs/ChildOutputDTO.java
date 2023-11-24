package com.victoricare.api.dtos.outputs;

import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class ChildOutputDTO extends PersonOutputDTO {
    private FamilyOutputDTO family;
}
