package com.victoricare.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.victoricare.api.entities.Code;
import com.victoricare.api.entities.User;
import com.victoricare.api.services.impl.CodeService.ECodeLength;

public interface ICodeService {

    Code doCreate(ECodeLength length);

    Code doGet(String id);

    void doDelete(User onlineUser, String id);

    Page<Code> doPage(Pageable pageable);
}
