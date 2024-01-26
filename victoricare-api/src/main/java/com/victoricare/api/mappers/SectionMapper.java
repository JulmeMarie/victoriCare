package com.victoricare.api.mappers;

import com.victoricare.api.dtos.outputs.SectionOutputDTO;
import com.victoricare.api.entities.Section;

import jakarta.annotation.Nullable;

public class SectionMapper {

    public static SectionOutputDTO toOutput(@Nullable Section section) {
        if (section == null)
            return null;
        return SectionOutputDTO.builder()
                .id(section.getId())
                .order(section.getOrder())
                .title(section.getTitle())
                .text(section.getText())
                .creationDate(section.getCreationDate())
                .updateDate(section.getUpdateDate())
                .deletionDate(section.getDeletionDate())
                .creationAuthorId(section.getCreationAuthorId())
                .deletionAuthorId(section.getDeletionAuthorId())
                .updateAuthorId(section.getUpdateAuthorId())
                .doc(DocMapper.toOutput(section.getDoc()))
                .medias(section.getMedias().stream().map(MediaMapper::toOutput).toList())
                .build();

    }

}
