package com.victoricare.api.mappers;

import com.victoricare.api.dtos.outputs.RecoveryOutputDTO;
import com.victoricare.api.entities.Recovery;

public class RecoveryMapper {

    public static RecoveryOutputDTO toOutput(Recovery recovery) {
        if (recovery == null)
            return null;

        return RecoveryOutputDTO.builder()
                .id(recovery.getId())
                .email(recovery.getEmail())
                .pseudo(recovery.getPseudo())
                .creationDate(recovery.getCreationDate())
                .codeExpirationDate(recovery.getCodeExpirationDate())
                .validatingDate(recovery.getValidatingDate())
                .deletionDate(recovery.getDeletionDate())
                .ip(recovery.getIp())
                .browser(recovery.getBrowser())
                .code(recovery.getCode())
                .build();
    }

}
