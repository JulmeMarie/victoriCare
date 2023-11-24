package com.victoricare.api.dtos.outputs;

import java.io.Serializable;
import java.util.Date;
import lombok.Builder;

@Builder
public class BanishmentOutputDTO implements Serializable {

    private Integer id;

    private String ip;

    private String browser;

    private Date creationDate;
}
