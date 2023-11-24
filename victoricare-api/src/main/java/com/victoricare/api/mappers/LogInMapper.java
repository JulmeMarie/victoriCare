package com.victoricare.api.mappers;

import com.victoricare.api.dtos.outputs.LogInOutputDTO;
import com.victoricare.api.entities.LogIn;

public class LogInMapper {

    public static LogInOutputDTO toOutput(LogIn logIn) {
        if (logIn == null)
            return null;

        return LogInOutputDTO.builder()
                .id(logIn.getId())
                .pseudo(logIn.getPseudo())
                .email(logIn.getEmail())
                .browser(logIn.getBrowser())
                .ip(logIn.getIp())
                .creationDate(logIn.getCreationDate())
                .expirationDate(logIn.getExpirationDate())
                .updateDate(logIn.getUpdateDate())
                .deletionDate(logIn.getDeletionDate())
                .token(logIn.getToken())
                .roles(logIn.getRoles())
                .rights(logIn.getRights())
                .deletionAuthorId(logIn.getDeletionAuthorId())
                .updateAuthorId(logIn.getUpdateAuthorId())
                .creationAuthor(UserMapper.toOutput(logIn.getCreationAuthor()))
                .build();
    }

}
