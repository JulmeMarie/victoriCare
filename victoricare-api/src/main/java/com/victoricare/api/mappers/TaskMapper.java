package com.victoricare.api.mappers;

import com.victoricare.api.dtos.outputs.TaskOutputDTO;
import com.victoricare.api.entities.Task;
import jakarta.annotation.Nullable;

public class TaskMapper {

    public static TaskOutputDTO toOutput(@Nullable Task task) {
        if (task == null)
            return null;

        return TaskOutputDTO.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .creationDate(task.getCreationDate())
                .updateDate(task.getUpdateDate())
                .deletionDate(task.getDeletionDate())
                .creationAuthorId(task.getCreationAuthorId())
                .updateAuthorId(task.getUpdateAuthorId())
                .build();

    }

}
