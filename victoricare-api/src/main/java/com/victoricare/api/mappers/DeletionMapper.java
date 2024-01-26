package com.victoricare.api.mappers;

import com.victoricare.api.dtos.outputs.DeletionOutputDTO;
import com.victoricare.api.entities.DeletionHistory;

public class DeletionMapper {

    public static DeletionOutputDTO toOutput(DeletionHistory deletion) {
        if (deletion == null)
            return null;
        return DeletionOutputDTO.builder()
                .id(deletion.getId())
                .table(deletion.getTable())
                .line(deletion.getLine())
                .creationDate(deletion.getCreationDate())
                .creationAuthorId(deletion.getCreationAuthorId())
                .build();
    }

}
