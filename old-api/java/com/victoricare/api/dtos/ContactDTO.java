package com.victoricare.api.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {

    private Integer id;

    private String name;

	private String email;

	private String text;

	private String subject;

	private String response;
}
