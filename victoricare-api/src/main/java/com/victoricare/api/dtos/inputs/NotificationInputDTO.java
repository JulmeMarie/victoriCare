package com.victoricare.api.dtos.inputs;

import lombok.Data;

@Data
public class NotificationInputDTO {

	private Long id;

	private String title;

	private Long destinationId;

	private String type;

	private String content;
}
