package com.victoricare.api.dtos.inputs;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChildInputDTO extends PersonInputDTO {

    private Integer familyId;

    private Integer attachmentFamilyId;
}
