package com.victoricare.api.dtos.inputs;

import lombok.Data;

@Data
public class MediaInputDTO {

	private Integer id;

	private String title;

	private String name;

	private String nature;

	private String position;

	private Integer sectionId;

	private Integer creationAuthorId;
}
