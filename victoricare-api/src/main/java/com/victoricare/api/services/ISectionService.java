package com.victoricare.api.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.victoricare.api.dtos.inputs.SectionInputDTO;
import com.victoricare.api.entities.Section;
import com.victoricare.api.entities.User;

public interface ISectionService {

    Section doCreate(User onlineUser, SectionInputDTO dto);

    Section doUpdate(User onlineUser, SectionInputDTO dto, Integer id);

    void doCancel(User user, Integer id);

    void doDelete(User onlineUser, Integer id);

    Section doGet(User onlineUser, Integer id);

    Page<Section> doPage(Pageable pageable);

    List<Section> doListByDoc(User onlineUser, Integer docId);
}
