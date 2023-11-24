package com.victoricare.api.mappers;

import com.victoricare.api.dtos.outputs.UserOutputDTO;
import com.victoricare.api.dtos.outputs.UserOutputDTO.UserOutputDTOBuilder;
import com.victoricare.api.entities.User;
import jakarta.annotation.Nullable;

public class UserMapper extends PersonMapper {

    public static UserOutputDTO toOutput(@Nullable User user) {
        if (user == null)
            return null;

        UserOutputDTOBuilder<?, ?> builder = (UserOutputDTOBuilder<?, ?>) setProperties(UserOutputDTO.builder(),
                user);

        return builder
                .pseudo(user.getPseudo())
                .email(user.getEmail())
                .banishmentDate(user.getBanishmentDate())
                .rights(user.getRights())
                .roles(user.getRoles())
                .signupId(user.getSignUpId())
                .banishmentAuthorId(user.getBanishmentAuthorId())
                .creationAuthorId(user.getCreationAuthorId())
                .deletionAuthorId(user.getDeletionAuthorId())
                .build();
    }
}
