package com.victoricare.api.mappers;

import com.victoricare.api.dtos.outputs.ChildCareTaskOutputDTO;
import com.victoricare.api.entities.ChildCareTask;
import jakarta.annotation.Nullable;

public class ChildCareTaskMapper {

    public static ChildCareTaskOutputDTO toOutput(@Nullable ChildCareTask task) {
        if (task == null)
            return null;

        return ChildCareTaskOutputDTO.builder()
                .id(task.getId())
                .creationDate(task.getCreationDate())
                .updateDate(task.getAupdateDate())
                .deletionDate(task.getDeletionDate())
                .childId(task.getChildId())
                .creationAuthorId(task.getCreationAuthorId())
                .updateAuthorId(task.getAupdateAuthorId())
                .deletionAuthorId(task.getDeletionAuthorId())
                .childCare(ChildCareMapper.toOutput(task.getChildCare()))
                .build();
    }
}
