package com.victoricare.api.mappers;

import com.victoricare.api.dtos.outputs.FamilyOutputDTO;
import com.victoricare.api.entities.Family;
import jakarta.annotation.Nullable;

public class FamilyMapper {

    public static FamilyOutputDTO toOutput(@Nullable Family family) {
        if (family == null)
            return null;
        return FamilyOutputDTO.builder()
                .id(family.getId())
                .creationDate(family.getCreationDate())
                .updateDate(family.getUpdateDate())
                .deletionDate(family.getDeletionDate())
                .creationAuthorId(family.getCreationAuthorId())
                .updateAuthorId(family.getUpdateAuthorId())
                .deletionAuthorId(family.getDeletionAuthorId())
                .parentOne(family.getParentOne())
                .parentTwo(family.getParentTwo())
                .babySitter(family.getBabySitter())
                .children(family.getChildren().stream().map(ChildMapper::toOutput).toList())
                .build();
    }

}
