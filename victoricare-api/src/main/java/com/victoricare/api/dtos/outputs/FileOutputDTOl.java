package com.victoricare.api.dtos.outputs;

import java.io.Serializable;

import org.springframework.core.io.Resource;
import lombok.Data;

@Data
public class FileOutputDTOl implements Serializable {
	private static final long serialVersionUID = 1L;

	private String filename;
	private Resource resource;

}
