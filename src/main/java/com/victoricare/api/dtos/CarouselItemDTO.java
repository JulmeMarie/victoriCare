package com.victoricare.api.dtos;

import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarouselItemDTO {

	@Nullable
	private Integer id;

	@NotNull
	private Short order;

	@NotNull
	private MultipartFile mFile;

	@Nullable
	private String title;

	@Nullable
	private String description;
}
