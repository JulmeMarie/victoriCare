package com.victoricare.api.dtos.outputs;

import java.io.Serializable;
import java.util.Date;
import lombok.Builder;

@Builder
public class NotificationOutputDTO implements Serializable {

    private Long id;

    private String content;

    private Long destinationId;

    private String type;

    private Date creationDate;

    private Date readingDate;

    private Date viewDate;

    private Date deletionDate;
}
