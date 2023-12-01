package com.victoricare.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemDTO {

    private Integer id;

	private String href;

	private String text;

	private String type;

	private boolean active;

	private String group;

	private Integer order;

	private String target;

	private String htmlClass;

	private String icon;
}
