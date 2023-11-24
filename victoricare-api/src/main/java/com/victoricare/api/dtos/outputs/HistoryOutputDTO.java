package com.victoricare.api.dtos.outputs;

import java.io.Serializable;
import java.util.Date;
import lombok.Builder;

@Builder
public class HistoryOutputDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String table;

	private Date creationDate;

	private String line;

	private Integer creationAuthorId;
}
