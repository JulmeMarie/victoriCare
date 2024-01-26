package com.victoricare.api.services.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.victoricare.api.dtos.inputs.SectionInputDTO;
import com.victoricare.api.entities.Section;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.EMessage;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.repositories.ISectionRepository;
import com.victoricare.api.services.IDocService;
import com.victoricare.api.services.ISectionService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SectionService implements ISectionService {

    @Autowired
    private ISectionRepository repository;

    @Autowired
    private IDocService docService;

    @Override
    public Section doCreate(User onlineUser, SectionInputDTO dto) {
        Section section = new Section();
        section.setCreationAuthorId(onlineUser.getId());
        section.setCreationDate(new Date());
        section.setText(dto.getText());
        section.setTitle(dto.getTitle());
        section.setDoc(this.docService.doGet(onlineUser, dto.getDocId()));
        this.repository.save(section);
        return section;
    }

    @Override
    public Section doUpdate(User onlineUser, SectionInputDTO dto, Integer id) {
        Section section = this.doGet(onlineUser, id);

        if (section.getDeletionDate() != null) {
            throw new ResourceNotFoundException(EMessage.SECTION_HAS_BEEN_CANCELED);
        }
        if (!onlineUser.getId().equals(section.getCreationAuthorId())) {
            throw new ActionNotAllowedException(EMessage.USER_NOT_AUTHOR);
        }
        section.setText(dto.getText());
        section.setTitle(dto.getTitle());
        section.setUpdateDate(new Date());
        section.setUpdateAuthorId(onlineUser.getId());
        this.repository.save(section);
        return section;
    }

    @Override
    public void doCancel(User user, Integer id) {
        Section exisitingSection = this.doGet(user, id);

        if (exisitingSection.getDeletionDate() != null) {
            throw new ResourceNotFoundException(EMessage.SECTION_HAS_BEEN_CANCELED);
        }

        if (!user.getId().equals(exisitingSection.getCreationAuthorId())) {
            throw new ActionNotAllowedException(EMessage.USER_NOT_AUTHOR);
        }
        exisitingSection.setDeletionDate(new Date());
        exisitingSection.setDeletionAuthorId(user.getId());
        this.repository.save(exisitingSection);
    }

    @Override
    public void doDelete(User onlineUser, Integer id) {
        Section section = this.doGet(onlineUser, id);
        this.repository.delete(section);

        log.info("User : [" + onlineUser.getUniqueIdentifier() + "] has deleted "
                + Section.class + " with id : [" + id + "]");
    }

    @Override
    public Section doGet(User onlineUser, Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMessage.SECTION_NOT_FOUND));
    }

    @Override
    public Page<Section> doPage(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public List<Section> doListByDoc(User onlineUser, Integer docId) {
        return this.repository.findAllByDoc(docId);
    }
}