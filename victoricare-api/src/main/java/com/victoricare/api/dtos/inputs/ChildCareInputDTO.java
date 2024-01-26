package com.victoricare.api.dtos.inputs;

import lombok.Data;
import java.util.Date;

@Data
public class ChildCareInputDTO {
    private Long id;
    private Date startDate;
    private Date endDate;
    private Integer creationFamilyId;
}