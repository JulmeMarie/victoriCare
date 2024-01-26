package com.victoricare.api.dtos.outputs;

import java.io.Serializable;
import java.util.Date;
import lombok.Builder;

@Builder
public class DeletionOutputDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String table;

	private String line;

	private Date creationDate;

	private Integer creationAuthorId;
}
