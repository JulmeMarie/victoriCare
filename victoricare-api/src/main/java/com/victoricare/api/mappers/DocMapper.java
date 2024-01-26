package com.victoricare.api.mappers;

import com.victoricare.api.dtos.outputs.DocOutputDTO;
import com.victoricare.api.entities.Doc;

import jakarta.annotation.Nullable;

public class DocMapper {

    public static DocOutputDTO toOutput(@Nullable Doc doc) {
        if (doc == null)
            return null;
        return DocOutputDTO.builder()
                .id(doc.getId())
                .title(doc.getTitle())
                .nature(doc.getNature())
                .creationDate(doc.getCreationDate())
                .updateDate(doc.getUpdateDate())
                .deletionDate(doc.getDeletionDate())
                .creationAuthor(UserMapper.toOutput(doc.getCreationAuthor()))
                .deletionAuthorId(doc.getDeletionAuthorId())
                .updateAuthorId(doc.getUpdateAuthorId())
                .sections(doc.getSections().stream().map(SectionMapper::toOutput).toList())
                .build();
    }

}
