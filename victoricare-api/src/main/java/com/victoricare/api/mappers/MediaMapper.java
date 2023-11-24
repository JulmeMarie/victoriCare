package com.victoricare.api.mappers;

import com.victoricare.api.dtos.outputs.MediaOutputDTO;
import com.victoricare.api.entities.Media;

public class MediaMapper {

    public static MediaOutputDTO toOutput(Media media) {
        if (media == null)
            return null;

        return MediaOutputDTO.builder()
                .id(media.getId())
                .title(media.getTitle())
                .name(media.getName())
                .type(media.getType())
                .position(media.getPosition())
                .creationDate(media.getCreationDate())
                .updateDate(media.getUpdateDate())
                .deletionDate(media.getDeletionDate())
                .creationAuthorId(media.getCreationAuthorId())
                .deletionAuthorId(media.getDeletionAuthorId())
                .updateAuthorId(media.getUpdateAuthorId())
                .section(SectionMapper.toOutput(media.getSection()))
                .build();
    }
}
