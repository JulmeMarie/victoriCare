package com.victoricare.api.models;

import org.springframework.core.io.Resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class FileModel {
	private String filename;
	private Resource resource;

}
