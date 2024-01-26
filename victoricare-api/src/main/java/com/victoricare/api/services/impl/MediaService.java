package com.victoricare.api.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.victoricare.api.dtos.inputs.MediaInputDTO;
import com.victoricare.api.entities.Media;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.EMessage;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.InvalidInputException;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.repositories.IMediaRepository;
import com.victoricare.api.services.IFileService;
import com.victoricare.api.services.IMediaService;
import com.victoricare.api.services.ISectionService;
import com.victoricare.api.services.impl.FileService.PropKey;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MediaService implements IMediaService {

    @Autowired
    private IFileService fileService;

    @Autowired
    private IMediaRepository repository;

    @Autowired
    private ISectionService sectionService;

    @Override
    public Media doCreate(User onlineUser, MediaInputDTO dto) {

        Map<PropKey, Object> properties = this.fileService.doUpload(dto.getMFile(), dto.getType());

        Media media = new Media();
        media.setCreationAuthorId(onlineUser.getId());
        media.setCreationDate(new Date());
        media.setTitle(dto.getTitle());
        media.setName((String) properties.get(PropKey.FILE_NAME));
        media.setFolder((String) properties.get(PropKey.FOLDER_NAME));
        media.setType(dto.getType());
        media.setPosition(dto.getPosition());
        media.setSection(this.sectionService.doGet(onlineUser, dto.getSectionId()));
        this.repository.save(media);
        return media;
    }

    @Override
    public Media doUpdate(User onlineUser, MediaInputDTO dto, Integer id) {
        Media media = this.doGet(onlineUser, id);
        if (media.getDeletionDate() != null) {
            throw new ResourceNotFoundException(EMessage.MEDIA_NOT_FOUND);
        }
        if (!onlineUser.getId().equals(media.getCreationAuthorId())) {
            throw new ActionNotAllowedException(EMessage.USER_NOT_AUTHOR);
        }
        media.setName(dto.getName());
        media.setType(dto.getType());
        media.setTitle(dto.getTitle());
        media.setPosition(dto.getPosition());
        media.setUpdateDate(new Date());
        media.setUpdateAuthorId(onlineUser.getId());
        this.repository.save(media);
        return media;
    }

    @Override
    public void doCancel(User user, Integer id) {
        Media exisitingMedia = this.doGet(user, id);

        if (exisitingMedia.getDeletionDate() != null) {
            throw new ResourceNotFoundException(EMessage.MEDIA_HAS_BEEN_CANCELED);
        }

        if (!user.getId().equals(exisitingMedia.getCreationAuthorId())) {
            throw new ActionNotAllowedException(EMessage.USER_NOT_AUTHOR);
        }
        exisitingMedia.setDeletionDate(new Date());
        exisitingMedia.setDeletionAuthorId(user.getId());
        this.repository.save(exisitingMedia);
    }

    @Override
    public void doDelete(User onlineUser, Integer id) {
        Media media = this.doGet(onlineUser, id);
        this.repository.delete(media);
        this.fileService.doDelete(media.getFolder(), media.getName());

        log.info("User : [" + onlineUser.getUniqueIdentifier() + "] has deleted "
                + Media.class + " with id : [" + id + "]");
    }

    @Override
    public Media doGet(User onlineUser, Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMessage.MEDIA_NOT_FOUND));
    }

    @Override
    public Page<Media> doPage(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public List<Media> doListByUser(User onlineUser, Integer userId) {
        return this.repository.findAllByUserId(userId);
    }

    @Override
    public List<Media> doListBySection(User onlineUser, Integer sectionId) {
        return this.repository.findAllBySection(sectionId);
    }

}