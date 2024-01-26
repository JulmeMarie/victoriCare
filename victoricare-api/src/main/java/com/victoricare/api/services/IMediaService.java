package com.victoricare.api.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.victoricare.api.dtos.inputs.MediaInputDTO;
import com.victoricare.api.entities.Media;
import com.victoricare.api.entities.User;

public interface IMediaService {

    Media doCreate(User onlineUser, MediaInputDTO dto);

    Media doUpdate(User onlineUser, MediaInputDTO dto, Integer id);

    void doCancel(User user, Integer id);

    void doDelete(User onlineUser, Integer id);

    Media doGet(User onlineUser, Integer id);

    Page<Media> doPage(Pageable pageable);

    List<Media> doListBySection(User onlineUser, Integer sectionId);

    List<Media> doListByUser(User onlineUser, Integer userId);
}
