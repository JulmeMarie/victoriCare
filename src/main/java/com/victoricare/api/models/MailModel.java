package com.victoricare.api.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class MailModel {
	private List<String> to = new ArrayList<>();
	private String subject;
	private String template;
	private Map<String, Object> properties = new HashMap<>();
}
