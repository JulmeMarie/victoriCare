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
import org.springframework.security.crypto.password.PasswordEncoder;
import com.victoricare.api.services.impl.RecoveryService;
import com.victoricare.api.validators.impl.PageValidator;
import com.victoricare.api.repositories.IRecoveryRepository;
import com.victoricare.api.repositories.IUserRepository;
import com.victoricare.api.security.JWTToken;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import com.victoricare.api.dtos.inputs.RecoveryInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.entities.Recovery;
import com.victoricare.api.entities.User;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.InvalidInputException;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.mailers.IRecoveryMailer;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecoveryServiceTest {
    private static final String EMAIL = "test@email.fr";
    private static final String USERNAME = "TEST31";
    private static final String PASS = "Test31@";
    private static final String BROWSER = "Firefox";
    private static final String IP = "127.0.0.1";
    private static final boolean TERMS_OK = true;
    private static final Integer USER_ID = 1;
    private static final Integer ID = 1;
    private static final Integer CODE = 123 - 456;

    @Mock
    private IRecoveryRepository repository;

    @Mock
    private JWTToken jwtToken;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IRecoveryMailer recoveryMailer;

    @Mock
    private IUserService userService;

    @InjectMocks
    private RecoveryService service;

    private Recovery recovery;
    private RecoveryInputDTO recoveryInputDTO;
    private User user;

    @BeforeEach
    public void setup() {
        service.setTimeout(120);
        this.initUser();
        this.initRecovery();
        this.initInput();
    }

    private void initUser() {
        this.user = User.builder()
                .id(USER_ID)
                .email(EMAIL)
                .pseudo(USERNAME)
                .build();
    }

    private void initRecovery() {
        this.recovery = Recovery.builder()
                .id(25)
                .email(EMAIL)
                .pseudo(USERNAME)
                .browser(BROWSER)
                .ip(IP)
                .build();
    }

    private void initInput() {
        recoveryInputDTO = new RecoveryInputDTO();
        recoveryInputDTO.setId(ID);
    }

    @Test
    @Order(1)
    public void createRecoveryMustBeOKTest() {

        // precondition
        given(userService.doGet(EMAIL)).willReturn(this.user);
        given(repository.save(Mockito.any(Recovery.class))).willReturn(recovery);
        willDoNothing().given(recoveryMailer).sendCode(Mockito.any(Recovery.class));

        // action
        Recovery savedRecovery = service.doCreate(jwtToken, EMAIL);

        // verify the output
        assertThat(savedRecovery.getCreationDate()).isNotNull();
        assertThat(savedRecovery.getCode()).isNotNull();
        assertThat(savedRecovery.getCodeDate()).isNotNull();
        assertThat(savedRecovery.getCodeExpirationDate()).isNotNull();
    }

    @Test
    @Order(1)
    public void createRecoveryWhenUserIdDeletedThrowExceptionTest() {
        user.setAccountDeletionDate(new Date());
        given(userService.doGet(EMAIL)).willReturn(this.user);

        assertThrows(ResourceNotFoundException.class, () -> service.doCreate(jwtToken, EMAIL));
    }

    @Test
    @Order(1)
    public void resendRecoveryMustBeOKTest() {

        // precondition
        given(this.repository.findById(ID)).willReturn(Optional.of(this.recovery));
        given(repository.save(Mockito.any(Recovery.class))).willReturn(recovery);
        willDoNothing().given(recoveryMailer).sendCode(Mockito.any(Recovery.class));

        // action
        Recovery savedRecovery = service.doResend(jwtToken, EMAIL, ID);

        // verify the output
        assertThat(savedRecovery.getCode()).isNotNull();
        assertThat(savedRecovery.getCodeDate()).isNotNull();
        assertThat(savedRecovery.getCodeExpirationDate()).isNotNull();
    }

    @Test
    @Order(1)
    public void resendDeletedRecoveryMustBeOKTest() {
        this.recovery.setDeletionDate(new Date());

        // precondition
        given(this.repository.findById(ID)).willReturn(Optional.of(this.recovery));

        // verify the output
        assertThrows(ActionNotAllowedException.class, () -> service.doResend(jwtToken, EMAIL, ID));
    }

    @Test
    @Order(1)
    public void resendValidatedRecoveryMustBeOKTest() {
        this.recovery.setValidatingDate(new Date());

        // precondition
        given(this.repository.findById(ID)).willReturn(Optional.of(this.recovery));

        // verify the output
        assertThrows(ActionNotAllowedException.class, () -> service.doResend(jwtToken, EMAIL, ID));
    }

    @Test
    @Order(1)
    public void resendWithFakeEmailRecoveryMustBeOKTest() {
        // this.recovery.setDeletionDate(new Date());
        String fake_email = "fake@example.com";

        // precondition
        given(this.repository.findById(ID)).willReturn(Optional.of(this.recovery));

        // verify the output
        assertThrows(ActionNotAllowedException.class, () -> service.doResend(jwtToken, fake_email, ID));
    }

    @Test
    @Order(2)
    public void validateRecoveryMustBeOkTest() {
        Date expirationDate = new Date(new Date().getTime() + 120 * 60 * 60);
        recovery.setCode(String.valueOf(CODE));
        recovery.setCodeExpirationDate(expirationDate);

        given(repository.findById(recovery.getId())).willReturn(Optional.of(recovery));
        given(repository.save(recovery)).willReturn(recovery);
        willDoNothing().given(recoveryMailer).sendConfirmation(Mockito.any(Recovery.class));

        // action
        Recovery savedRecovery = service.doValidate(jwtToken, recovery.getId(), CODE);

        // verify the output
        assertThat(savedRecovery.getValidatingDate()).isNotNull();
    }

    @Test
    @Order(2)
    public void validateRecoveryWithWrongCodeMustThrowExceptionTest() {
        Date expirationDate = new Date(new Date().getTime() + 10 * 1000); // 10 secods after now
        final Integer WRONG_CODE = 987654;
        recovery.setCode(String.valueOf(CODE));
        recovery.setCodeExpirationDate(expirationDate);

        given(repository.findById(recovery.getId())).willReturn(Optional.of(recovery));

        // action
        assertThrows(InvalidInputException.class, () -> service.doValidate(jwtToken, recovery.getId(), WRONG_CODE));
    }

    @Test
    @Order(2)
    public void validateRecoveryAfterExpirationDateMustThrowExceptionTest() {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() - 1 * 1000); // 1 second before now
        recovery.setCode(String.valueOf(CODE));
        recovery.setCodeExpirationDate(expirationDate);

        given(repository.findById(recovery.getId())).willReturn(Optional.of(recovery));
        given(repository.save(recovery)).willReturn(recovery);

        // action
        assertThrows(InvalidInputException.class, () -> service.doValidate(jwtToken, recovery.getId(), CODE));
    }

    @Test
    @Order(2)
    public void validateUnExistingRecoveryMustThrowExceptionTest() {
        // precondition
        given(repository.findById(recovery.getId())).willReturn(Optional.empty());

        // action
        assertThrows(ResourceNotFoundException.class, () -> service.doValidate(jwtToken, recovery.getId(), CODE));
    }

    @Test
    @Order(2)
    public void doCancelRecoveryMustBeOKTest() {
        given(repository.findById(recovery.getId())).willReturn(Optional.of(recovery));
        given(repository.save(recovery)).willReturn(recovery);
        service.doCancel(recovery.getId());
        assertThat(recovery.getDeletionDate()).isNotNull();
    }

    @Test
    @Order(2)
    public void doCancelDeletedRecoveryThrowExceptionTest() {
        recovery.setDeletionDate(new Date());
        given(repository.findById(recovery.getId())).willReturn(Optional.of(recovery));
        assertThrows(ActionNotAllowedException.class, () -> service.doCancel(recovery.getId()));
    }

    @Test
    @Order(2)
    public void doDeleteRecoveryMustBeOKTest() {
        // precondition
        given(repository.findById(recovery.getId())).willReturn(Optional.of(recovery));

        willDoNothing().given(repository).delete(recovery);
        service.doDelete(user, recovery.getId());
    }

    @Test

    @Order(3)
    public void getPageTest() {
        PageInputDTO page = new PageInputDTO();
        Pageable pageable = PageValidator.getPageable(Recovery.class, page);
        Page<Recovery> recoveryPage = new PageImpl<Recovery>(List.of(recovery));

        // precondition
        given(repository.findAll(pageable)).willReturn(recoveryPage);

        // action
        Page<Recovery> recoveryList = service.doPage(pageable);

        // verify
        System.out.println(recoveryList);
        assertThat(recoveryList).isNotNull();
        assertThat(recoveryList.getSize()).isEqualTo(1);
        assertThat(recoveryList.getContent().get(0).equals(recovery));
    }

    @Test
    @Order(2)
    public void doGetMustBeOKTest() {
        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(recovery));

        // action
        Recovery existingRecovery = service.doGet(ID);

        // verify
        assertThat(existingRecovery).isNotNull();
    }

    @Test
    @Order(2)
    public void findByEmailMustBeOKTest() {
        // precondition
        given(repository.findByEmail(EMAIL)).willReturn(recovery);

        // action
        Recovery existingRecovery = service.findByEmail(EMAIL);

        // verify
        assertThat(existingRecovery).isNotNull();
    }

    @Test
    @Order(2)
    public void doGetNonExistingRecoveryMustThrowExceptionTest() {
        // precondition
        given(repository.findById(ID)).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.doGet(ID));
    }
}