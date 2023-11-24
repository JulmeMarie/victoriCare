package com.victoricare.api.dtos.outputs;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Builder;

@Builder
public class SectionOutputDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer order;

    private String title;

    private String text;

    private Date creationDate;

    private Date updateDate;

    private Date deletionDate;

    private Integer creationAuthorId;

    private Integer deletionAuthorId;

    private Integer updateAuthorId;

    private DocOutputDTO doc;

    private List<MediaOutputDTO> medias;

}
