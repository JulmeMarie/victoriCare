package com.victoricare.api.dtos.outputs;

import java.io.Serializable;
import java.util.Date;
import lombok.Builder;

@Builder
public class MediaOutputDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String title;

    private String name;

    private String type;

    private String position;

    private Date creationDate;

    private Date updateDate;

    private Date deletionDate;

    private Integer creationAuthorId;

    private Integer deletionAuthorId;

    private Integer updateAuthorId;

    private SectionOutputDTO section;
}