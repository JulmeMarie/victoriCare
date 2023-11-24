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
import com.victoricare.api.services.impl.SignUpService;
import com.victoricare.api.validators.impl.PageValidator;
import com.victoricare.api.repositories.ISignUpRepository;
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
import com.victoricare.api.dtos.inputs.SignUpInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.entities.SignUp;
import com.victoricare.api.entities.User;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.InvalidInputException;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.mailers.ISignUpMailer;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SignUpServiceTest {
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
    private ISignUpRepository repository;

    @Mock
    private JWTToken jwtToken;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private IUserService userService;

    @Mock
    private ISignUpMailer signUpMailer;

    @InjectMocks
    private SignUpService service;

    private SignUp signUp;
    private SignUpInputDTO signUpInputDTO;
    private User user;

    @BeforeEach
    public void setup() {
        service.setTimeout(120);
        this.initUser();
        this.initSignUp();
        this.initInput();
    }

    private void initUser() {
        this.user = User.builder()
                .id(USER_ID)
                .email(EMAIL)
                .pseudo(USERNAME)
                .build();
    }

    private void initSignUp() {
        this.signUp = SignUp.builder()
                .id(25)
                .email(EMAIL)
                .pseudo(USERNAME)
                .pass(PASS)
                .browser(BROWSER)
                .ip(IP)
                .build();
    }

    private void initInput() {
        signUpInputDTO = new SignUpInputDTO();
        signUpInputDTO.setEmail(EMAIL);
        signUpInputDTO.setPseudo(USERNAME);
        signUpInputDTO.setPass(PASS);
        signUpInputDTO.setTermsOK(TERMS_OK);
    }

    @Test
    @Order(1)
    public void saveSignUpMustBeOKTest() {

        // precondition
        given(userService.doListByEmailOrPseudo(EMAIL, USERNAME)).willReturn(List.of());// No user exists
        given(repository.findByEmailOrPseudo(EMAIL, USERNAME)).willReturn(List.of()); // No Sign-up exists
        given(repository.save(Mockito.any(SignUp.class))).willReturn(signUp);
        willDoNothing().given(signUpMailer).sendCode(Mockito.any(SignUp.class));

        // action
        SignUp savedSignUp = service.doCreate(jwtToken, encoder, signUpInputDTO);

        // verify the output
        assertThat(savedSignUp.getCreationDate()).isNotNull();
        assertThat(savedSignUp.getCode()).isNotNull();
        assertThat(savedSignUp.getCodeDate()).isNotNull();
        assertThat(savedSignUp.getCodeExpirationDate()).isNotNull();
    }

    @Test
    @Order(1)
    public void saveSignUpWhenUserExistMustThrowExceptionTest() {

        // precondition
        given(userService.doListByEmailOrPseudo(EMAIL, USERNAME)).willReturn(List.of(user)); // User exists

        // action
        assertThrows(InvalidInputException.class, () -> service.doCreate(jwtToken, encoder, signUpInputDTO));
    }

    @Test
    @Order(1)
    public void saveSignUpWhenUserExistWithSamePseudoButDifferentEmailMustThrowExceptionTest() {

        // precondition
        user.setEmail("other_user_email@example.com");
        given(userService.doListByEmailOrPseudo(EMAIL, USERNAME)).willReturn(List.of(user)); // user exists with //
                                                                                             // different email

        // action
        assertThrows(InvalidInputException.class, () -> service.doCreate(jwtToken, encoder, signUpInputDTO));
    }

    @Test
    @Order(1)
    public void saveSignUpWhenSignUpExistsWithSamePseudoButDifferentEmailMustThrowExceptionTest() {

        signUp.setEmail("other_user_email@example.com");
        given(repository.findByEmailOrPseudo(EMAIL, USERNAME)).willReturn(List.of(signUp)); // sign up exists with
                                                                                            // different email
        // action
        assertThrows(InvalidInputException.class, () -> service.doCreate(jwtToken, encoder, signUpInputDTO));
    }

    @Test
    @Order(1)
    public void saveSignUpWhenSignUpExistWithSameEmailMustBeOKTest() {

        // precondition
        given(userService.doListByEmailOrPseudo(EMAIL, USERNAME)).willReturn(List.of()); // No user exists
        given(repository.findByEmailOrPseudo(EMAIL, USERNAME)).willReturn(List.of(signUp)); // sign up exists
        given(repository.save(Mockito.any(SignUp.class))).willReturn(signUp);
        willDoNothing().given(signUpMailer).sendCode(Mockito.any(SignUp.class));

        // action
        SignUp savedSignUp = service.doCreate(jwtToken, encoder, signUpInputDTO);

        // verify the output
        assertThat(savedSignUp.getCode()).isNotNull();
        assertThat(savedSignUp.getCodeDate()).isNotNull();
        assertThat(savedSignUp.getCodeExpirationDate()).isNotNull();
    }

    @Test
    @Order(2)
    public void validateSignUpMustBeOkTest() {
        Date expirationDate = new Date(new Date().getTime() + 120 * 60 * 60);
        signUp.setCode(String.valueOf(CODE));
        signUp.setCodeExpirationDate(expirationDate);

        given(repository.findById(signUp.getId())).willReturn(Optional.of(signUp));
        given(repository.save(signUp)).willReturn(signUp);
        willDoNothing().given(signUpMailer).sendConfirmation(Mockito.any(SignUp.class));

        // action
        SignUp savedSignUp = service.doValidate(jwtToken, signUp.getId(), CODE);

        // verify the output
        assertThat(savedSignUp.getValidatingDate()).isNotNull();
    }

    @Test
    @Order(2)
    public void validateSignUpWithWrongCodeMustThrowExceptionTest() {
        Date expirationDate = new Date(new Date().getTime() + 10 * 1000); // 10 secods after now
        final Integer WRONG_CODE = 987654;
        signUp.setCode(String.valueOf(CODE));
        signUp.setCodeExpirationDate(expirationDate);

        given(repository.findById(signUp.getId())).willReturn(Optional.of(signUp));

        // action
        assertThrows(InvalidInputException.class, () -> service.doValidate(jwtToken, signUp.getId(), WRONG_CODE));
    }

    @Test
    @Order(2)
    public void validateSignUpAfterExpirationDateMustThrowExceptionTest() {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() - 1 * 1000); // 1 second before now
        signUp.setCode(String.valueOf(CODE));
        signUp.setCodeExpirationDate(expirationDate);

        given(repository.findById(signUp.getId())).willReturn(Optional.of(signUp));
        given(repository.save(signUp)).willReturn(signUp);

        // action
        assertThrows(InvalidInputException.class, () -> service.doValidate(jwtToken, signUp.getId(), CODE));
    }

    @Test
    @Order(2)
    public void validateUnExistingSignUpMustThrowExceptionTest() {
        // precondition
        given(repository.findById(signUp.getId())).willReturn(Optional.empty());

        // action
        assertThrows(ResourceNotFoundException.class, () -> service.doValidate(jwtToken, signUp.getId(), CODE));
    }

    @Test
    @Order(2)
    public void doCancelSignUpMustBeOKTest() {
        given(repository.findById(signUp.getId())).willReturn(Optional.of(signUp));
        given(repository.save(signUp)).willReturn(signUp);
        service.doCancel(signUp.getId());
        assertThat(signUp.getCancelationDate()).isNotNull();
    }

    @Test
    @Order(2)
    public void doCancelUnExistingSignUpThrowExceptionTest() {
        given(repository.findById(signUp.getId())).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.doCancel(signUp.getId()));
    }

    @Test
    @Order(2)
    public void doCancelValidatedSignUpThrowExceptionTest() {
        signUp.setValidatingDate(new Date());
        given(repository.findById(signUp.getId())).willReturn(Optional.of(signUp));
        assertThrows(ActionNotAllowedException.class, () -> service.doCancel(signUp.getId()));
    }

    @Test
    @Order(2)
    public void doDeleteSignUpMustBeOKTest() {
        // precondition
        given(repository.findById(signUp.getId())).willReturn(Optional.of(signUp));

        willDoNothing().given(repository).delete(signUp);
        service.doDelete(user, signUp.getId());
    }

    @Test

    @Order(3)
    public void getPageTest() {
        PageInputDTO page = new PageInputDTO();
        Pageable pageable = PageValidator.getPageable(SignUp.class, page);
        Page<SignUp> signUpPage = new PageImpl<SignUp>(List.of(signUp));

        // precondition
        given(repository.findAll(pageable)).willReturn(signUpPage);

        // action
        Page<SignUp> signUpList = service.doPage(pageable);

        // verify
        System.out.println(signUpList);
        assertThat(signUpList).isNotNull();
        assertThat(signUpList.getSize()).isEqualTo(1);
        assertThat(signUpList.getContent().get(0).equals(signUp));
    }

    @Test
    @Order(2)
    public void doGetMustBeOKTest() {
        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(signUp));

        // action
        SignUp existingSignUp = service.doGet(ID);

        // verify
        assertThat(existingSignUp).isNotNull();
    }

    @Test
    @Order(2)
    public void doGetNonExistingSignUpMustThrowExceptionTest() {
        // precondition
        given(repository.findById(ID)).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.doGet(ID));
    }
}