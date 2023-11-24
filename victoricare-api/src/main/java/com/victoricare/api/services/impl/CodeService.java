package com.victoricare.api.services.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.victoricare.api.entities.Code;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.EMessage;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.repositories.ICodeRepository;
import com.victoricare.api.services.ICodeService;
import com.victoricare.api.utils.CustomRandom;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CodeService implements ICodeService {

    public static enum ECodeLength {
        SIX(6),
        TEN(10);

        private Integer value;

        ECodeLength(Integer codeLength) {
            this.value = codeLength;
        }

        public Integer getValue() {
            return this.value;
        }
    };

    @Autowired
    private ICodeRepository repository;

    public Code doCreate(ECodeLength length) {
        Code code = null;
        do {
            String codeString = CustomRandom.get2Chars() + "-" + CustomRandom.get4DigitCode();
            if (length == ECodeLength.TEN) {
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
    public Code doGet(String id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMessage.CODE_NOT_FOUND));
    }

    @Override
    public void doDelete(User onlineUser, String id) {
        Code code = this.doGet(id);
        this.repository.delete(code);

        log.info("User : [" + onlineUser.getUniqueIdentifier() + "] has deleted "
                + Code.class + " with id : [" + id + "]");
    }

    @Override
    public Page<Code> doPage(Pageable pageable) {
        return this.repository.findAll(pageable);
    }
}
