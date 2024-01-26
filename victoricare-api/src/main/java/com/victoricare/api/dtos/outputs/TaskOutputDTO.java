package com.victoricare.api.dtos.outputs;

import java.io.Serializable;
import java.util.Date;
import lombok.Builder;

@Builder
public class TaskOutputDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private Date creationDate;

    private Date updateDate;

    private Date deletionDate;

    private Integer creationAuthorId;

    private Integer updateAuthorId;
}