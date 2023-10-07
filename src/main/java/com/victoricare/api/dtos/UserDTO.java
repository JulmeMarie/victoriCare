package com.victoricare.api.dtos;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private Integer id;

    private String email;

    private String username;

    private String firstname;

    private String lastname;

    private String description;

    private  String role;

    private  String right;

	private MultipartFile mFile;

	private String urlFile;

	private boolean isContactAddressee;
}
