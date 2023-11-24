package com.victoricare.api.dtos.outputs;

import java.io.Serializable;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpOutputDTO implements Serializable {
    private Integer id;
    private String pseudo;
    private String email;
    private Date creationDate;
    private boolean termsOk;
    private Date cancelationDate;
    private Date validatingDate;
    private Date codeDate;
    private String browser;
    private String ip;
}