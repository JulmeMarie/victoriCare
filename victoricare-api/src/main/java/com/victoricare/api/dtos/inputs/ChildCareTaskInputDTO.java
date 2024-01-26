package com.victoricare.api.dtos.inputs;

import lombok.Data;

@Data
public class ChildCareTaskInputDTO {

    private Long id;

    private Integer childId;

    private Long childCareId;

    private String taskName;

    private String taskDescription;
}
