package com.victoricare.api.mappers;

import com.victoricare.api.dtos.outputs.BabySitterOutputDTO;
import com.victoricare.api.entities.BabySitter;
import jakarta.annotation.Nullable;

public class BabySitterMapper {

    public static BabySitterOutputDTO toOutput(@Nullable BabySitter babySitter) {
        if (babySitter == null)
            return null;
        return BabySitterOutputDTO.builder()
                .id(babySitter.getId())
                .code(babySitter.getCode())
                .firstname(babySitter.getFirstname())
                .lastname(babySitter.getLastname())
                .creationDate(babySitter.getCreationDate())
                .updateDate(babySitter.getUpdateDate())
                .deletionDate(babySitter.getDeletionDate())
                .updateAuthorId(babySitter.getUpdateAuthorId())
                .deletionAuthorId(babySitter.getDeletionAuthorId())
                .creationAuthorId(babySitter.getCreationAuthorId())
                .build();
    }

}
