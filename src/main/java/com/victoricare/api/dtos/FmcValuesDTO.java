package com.victoricare.api.dtos;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FmcValuesDTO {

	@Nullable
	public Integer id;

	@Nullable
	public String text;

	@Nullable
	public String type;

	@Nullable
	public String key;

	@Nullable
	public boolean active;
}
