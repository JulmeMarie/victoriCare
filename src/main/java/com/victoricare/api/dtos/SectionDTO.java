package com.victoricare.api.dtos;

import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionDTO {

	@Nullable
    private Integer id;

	@Nullable
	private String title;

	@Nullable
	private String text;

	@Nullable
	private MultipartFile leftMFile;

	@Nullable
	private MultipartFile rightMFile;

	@Nullable
	private Integer order;

	@Nullable
	private String htmlClass;
}
