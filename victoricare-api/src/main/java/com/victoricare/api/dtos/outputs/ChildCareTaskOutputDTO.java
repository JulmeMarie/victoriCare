package com.victoricare.api.dtos.outputs;

import java.io.Serializable;
import java.util.Date;
import lombok.Builder;

@Builder
public class ChildCareTaskOutputDTO implements Serializable {
    private Long id;

    private Date creationDate;

    private Date updateDate;

    private Date deletionDate;

    private Integer childId;

    private Integer creationAuthorId;

    private Integer updateAuthorId;

    private Integer deletionAuthorId;

    private ChildCareOutputDTO childCare;
}