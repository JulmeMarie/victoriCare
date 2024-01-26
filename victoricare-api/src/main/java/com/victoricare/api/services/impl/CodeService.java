package com.victoricare.api.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.victoricare.api.entities.Code;
import com.victoricare.api.enums.EMessage;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.repositories.ICodeRepository;
import com.victoricare.api.services.ICodeService;
import com.victoricare.api.utils.CustomRandom;

@Service
public class CodeService implements ICodeService {

    private static final int LENGTH_6_CODE = 6;
    private static final int LENGTH_10_CODE = 10;

    @Autowired
    private ICodeRepository repository;

    private Code doCreate(int length) {
        Code code = null;
        do {
            String codeString = CustomRandom.get2Chars() + "-" + CustomRandom.get4DigitCode();
            if (length == LENGTH_10_CODE) {
                codeString = CustomRandom.get4DigitCode() + "-" + codeString;
            }

            if (!this.repository.existsById(codeString)) {
                code = new Code();
                code.setCreationDate(new Date());
                code.setId(codeString);
                this.repository.save(code);
            }
        } while (code == null);
        return code;
    }

    @Override
    public Code doCreate6() {
        return this.doCreate(LENGTH_6_CODE);
    }

    @Override
    public Code doCreate10() {
        return this.doCreate(LENGTH_10_CODE);
    }

    @Override
    public Code doGet(String id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMessage.CODE_NOT_FOUND));
    }

    @Override
    public void doDelete(String id) {
        this.repository.findById(id).ifPresentOrElse(code -> this.repository.delete(code),
                () -> {
                    throw new ResourceNotFoundException(EMessage.CODE_NOT_FOUND);
                });
    }

    @Override
    public Page<Code> doList(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

}
