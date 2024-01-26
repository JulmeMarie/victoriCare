package com.victoricare.api.dtos.outputs;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import lombok.Builder;

@Builder
public class ChildCareOutputDTO implements Serializable {
    private Long id;

    private Date creationDate;

    private Date updateDate;

    private Date deletionDate;

    private Integer familyId;

    private Integer creationAuthorId;

    private Integer updateAuthorId;

    private Integer deletionAuthorId;

    private BabySitterOutputDTO babySitter;

    private Set<ChildCareTaskOutputDTO> childCareTaskOutputs;

}