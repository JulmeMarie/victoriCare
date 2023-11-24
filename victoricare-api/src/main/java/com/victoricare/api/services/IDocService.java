package com.victoricare.api.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.victoricare.api.dtos.inputs.DocInputDTO;
import com.victoricare.api.entities.Doc;
import com.victoricare.api.entities.User;

public interface IDocService {

    Doc doCreate(User onlineUser, DocInputDTO dto);

    Doc doUpdate(User onlineUser, DocInputDTO dto, Integer id);

    void doDelete(User onlineUser, Integer id);

    void doCancel(User user, Integer id);

    Doc doGet(User onlineUser, Integer id);

    Page<Doc> doPage(Pageable pageable);

    List<Doc> doListByUser(User onlineUser, Integer userId);
}
