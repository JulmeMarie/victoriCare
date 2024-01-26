package com.victoricare.api.dtos.outputs;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.victoricare.api.entities.BabySitter;
import com.victoricare.api.entities.User;
import lombok.Builder;

@Builder
public class FamilyOutputDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Date creationDate;

    private Date updateDate;

    private Date deletionDate;

    private Integer creationAuthorId;

    private Integer updateAuthorId;

    private User parentOne;

    private User parentTwo;

    private BabySitter babySitter;

    private Integer deletionAuthorId;

    private List<ChildOutputDTO> children;
}