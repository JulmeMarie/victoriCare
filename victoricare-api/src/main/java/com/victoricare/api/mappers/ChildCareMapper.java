package com.victoricare.api.mappers;

import com.victoricare.api.dtos.outputs.ChildCareOutputDTO;
import com.victoricare.api.entities.ChildCare;

public class ChildCareMapper {

    public static ChildCareOutputDTO toOutput(ChildCare childCare) {
        if (childCare == null)
            return null;

        return ChildCareOutputDTO.builder()
                .id(childCare.getId())
                .creationDate(childCare.getCreationDate())
                .updateDate(childCare.getUpdateDate())
                .deletionDate(childCare.getDeletionDate())
                .creationAuthorId(childCare.getCreationAuthorId())
                .updateAuthorId(childCare.getUpdateAuthorId())
                .deletionAuthorId(childCare.getDeletionAuthorId())
                .babySitter(BabySitterMapper.toOutput(childCare.getBabySitter()))
                .build();
    }

}
