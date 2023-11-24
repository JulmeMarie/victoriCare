package com.victoricare.api.dtos.outputs;

import java.io.Serializable;
import java.util.Date;
import lombok.Builder;

@Builder
public class BabySitterOutputDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String code;

    private String firstname;

    private String lastname;

    private Date creationDate;

    private Date updateDate;

    private Date deletionDate;

    private Integer updateAuthorId;

    private Integer deletionAuthorId;

    private Integer creationAuthorId;
}
