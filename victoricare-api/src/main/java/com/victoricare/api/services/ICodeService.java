package com.victoricare.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.victoricare.api.entities.Code;

public interface ICodeService {
    Code doCreate6();

    Code doCreate10();

    Code doGet(String id);

    void doDelete(String id);

    Page<Code> doList(Pageable pageable);
}
