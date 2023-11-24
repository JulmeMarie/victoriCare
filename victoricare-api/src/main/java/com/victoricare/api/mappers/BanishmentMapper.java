package com.victoricare.api.mappers;

import com.victoricare.api.dtos.outputs.BanishmentOutputDTO;
import com.victoricare.api.entities.Banishment;

public class BanishmentMapper {

    public static BanishmentOutputDTO toOutput(Banishment banishment) {
        return BanishmentOutputDTO.builder()
                .id(banishment.getId())
                .browser(banishment.getBrowser())
                .ip(banishment.getIp())
                .creationDate(banishment.getCreationDate())
                .build();
    }

}
