package com.victoricare.api.dtos.outputs;

import java.util.Date;
import lombok.Builder;

@Builder
public class LogInOutputDTO {

    private Long id;

    private String pseudo;

    private String email;

    private String browser;

    private String ip;

    private Date creationDate;

    private Date expirationDate;

    private Date updateDate;

    private Date deletionDate;

    private String token;

    private String roles;

    private String rights;

    private Integer deletionAuthorId;

    private Integer updateAuthorId;

    private UserOutputDTO creationAuthor;
}
