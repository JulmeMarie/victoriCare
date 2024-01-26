package com.victoricare.api.dtos.outputs;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Builder;

@Builder
public class DocOutputDTO implements Serializable {

    private Integer id;

    private String title;

    private String nature;

    private Date creationDate;

    private Date updateDate;

    private Date deletionDate;

    private UserOutputDTO creationAuthor;

    private Integer deletionAuthorId;

    private Integer updateAuthorId;

    private List<SectionOutputDTO> sections;

}