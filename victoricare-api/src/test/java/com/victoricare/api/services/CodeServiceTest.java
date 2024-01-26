package com.victoricare.api.services;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import com.victoricare.api.services.impl.CodeService;
import com.victoricare.api.validators.impl.PageValidator;
import com.victoricare.api.repositories.ICodeRepository;
import com.victoricare.api.repositories.IUserRepository;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.entities.Code;
import com.victoricare.api.entities.User;
import com.victoricare.api.exceptions.ResourceNotFoundException;

@DisplayName("CodeService Tests")
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CodeServiceTest {
    private static final Integer USER_ID = 1;
    private static final String ID = "1";

    @Mock
    private ICodeRepository repository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserService userService;

    @InjectMocks
    private CodeService service;

    private Code code;

    private User user = User.builder().id(USER_ID).build();

    @BeforeEach
    public void setup() {
        code = Code.builder().id(ID).build();
    }

    @Test
    @Order(1)
    public void shouldCreate6DigitCodeSuccessfully() {
        // precondition
        given(repository.existsById(Mockito.any(String.class))).willReturn(false);
        given(repository.save(Mockito.any(Code.class))).willReturn(code);

        // action
        Code savedCode = service.doCreate(CodeService.ECodeLength.SIX);

        // verify the output
        assertThat(savedCode.getCreationDate()).isNotNull(); // Verify creation date
        assertThat(savedCode.getId().replace("-", "").length()).isEqualTo(CodeService.ECodeLength.SIX.getValue());
    }

    @Test
    @Order(1)
    public void shouldCreate10igitCodeSuccessfully() {
        // precondition
        given(repository.existsById(Mockito.any(String.class))).willReturn(false);
        given(repository.save(Mockito.any(Code.class))).willReturn(code);

        // action
        Code savedCode = service.doCreate(CodeService.ECodeLength.TEN);

        // verify the output
        assertThat(savedCode.getCreationDate()).isNotNull(); // Verify creation date
        assertThat(savedCode.getId().replace("-", "").length()).isEqualTo(CodeService.ECodeLength.TEN.getValue());
    }

    @Test
    @Order(2)
    public void getCodeByIdTest() {
        code.setId(ID);
        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(code));

        // action
        Code existingCode = service.doGet(ID);

        // verify
        assertThat(existingCode).isNotNull();
    }

    @Test
    @Order(2)
    public void getDeletedCodeByIdThrowException() {
        // precondition
        given(repository.findById(code.getId())).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.doGet(code.getId()));
    }

    @Test
    @Order(3)
    public void getPageTest() {
        PageInputDTO page = new PageInputDTO();
        Pageable pageable = PageValidator.getPageable(Code.class, page);
        Page<Code> codePage = new PageImpl<Code>(List.of(code));

        // precondition
        given(repository.findAll(pageable)).willReturn(codePage);

        // action
        Page<Code> codeList = service.doPage(pageable);

        // verify
        assertThat(codeList).isNotNull();
        assertThat(codeList.getSize()).isEqualTo(1);
        assertThat(codeList.getContent().get(0).equals(code));
    }

    @Test
    @Order(9)
    public void deleteCodeTest() {

        given(repository.findById(code.getId())).willReturn(Optional.of(code));

        // precondition
        willDoNothing().given(repository).delete(code);

        // action
        service.doDelete(user, code.getId());

        // verify
        verify(repository, times(1)).delete(code);
    }
}