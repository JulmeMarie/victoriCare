package com.victoricare.api.dtos.inputs;

import lombok.Data;
import java.util.Set;

@Data
public class SectionInputDTO {

	private Integer id;

	private Integer order;

	private String title;

	private String text;

	private Integer docId;

	private Set<MediaInputDTO> medias;
}
