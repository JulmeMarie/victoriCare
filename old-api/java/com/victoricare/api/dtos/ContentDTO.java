package com.victoricare.api.dtos;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentDTO {

	@Nullable
	private Integer id;

	@NotNull
	private String type;

	@Nullable
	private String title;

	@Nullable
	private List<SectionDTO>sections = new ArrayList<>();
}
