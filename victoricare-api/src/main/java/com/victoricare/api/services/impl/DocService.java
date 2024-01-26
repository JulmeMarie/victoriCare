package com.victoricare.api.services.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.victoricare.api.dtos.inputs.DocInputDTO;
import com.victoricare.api.entities.Doc;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.EMessage;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.repositories.IDocRepository;
import com.victoricare.api.services.IDocService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DocService implements IDocService {

    @Autowired
    private IDocRepository repository;

    @Override
    public Doc doCreate(User onlineUser, DocInputDTO dto) {
        Doc doc = new Doc();
        doc.setCreationDate(new Date());
        doc.setCreationAuthor(onlineUser);
        doc.setNature(dto.getNature());
        doc.setTitle(dto.getTitle());
        this.repository.save(doc);
        return doc;
    }

    @Override
    public Doc doUpdate(User onlineUser, DocInputDTO dto, Integer id) {
        Doc doc = this.doGet(onlineUser, id);
        if (doc.getDeletionDate() != null) {
            throw new ActionNotAllowedException(EMessage.DOC_HAS_BEEN_CANCELED);
        }
        doc.setNature(dto.getNature());
        doc.setTitle(dto.getTitle());
        doc.setUpdateAuthorId(onlineUser.getId());
        doc.setUpdateDate(new Date());
        this.repository.save(doc);
        return doc;
    }

    @Override
    public void doCancel(User user, Integer id) {
        Doc exisitingDoc = this.doGet(user, id);

        if (exisitingDoc.getDeletionDate() != null) {
            throw new ActionNotAllowedException(EMessage.DOC_HAS_BEEN_CANCELED);
        }

        exisitingDoc.setDeletionDate(new Date());
        exisitingDoc.setDeletionAuthorId(user.getId());
        this.repository.save(exisitingDoc);
    }

    @Override
    public void doDelete(User onlineUser, Integer id) {
        Doc doc = this.doGet(onlineUser, id);
        this.repository.delete(doc);

        log.info("User : [" + onlineUser.getUniqueIdentifier() + "] has deleted "
                + Doc.class + " with id : [" + id + "]");
    }

    @Override
    public Doc doGet(User onlineUser, Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMessage.DOC_NOT_FOUND));
    }

    @Override
    public Page<Doc> doPage(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public List<Doc> doListByUser(User onlineUser, Integer userId) {
        return this.repository.findAllByUser(userId);
    }
}
