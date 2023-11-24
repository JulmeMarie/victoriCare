package com.victoricare.api.dtos.inputs;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class MediaInputDTO {

	private Integer id;

	private String title;

	private String name;

	private String type;

	private String position;

	private Integer sectionId;

	private Integer creationAuthorId;

	private MultipartFile mFile;
}