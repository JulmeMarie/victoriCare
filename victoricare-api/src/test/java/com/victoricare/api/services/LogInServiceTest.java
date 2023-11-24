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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.victoricare.api.services.impl.LogInService;
import com.victoricare.api.validators.impl.PageValidator;
import com.victoricare.api.repositories.ILogInRepository;
import com.victoricare.api.security.HttpHeaderWrapper;
import com.victoricare.api.security.JWTToken;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import com.victoricare.api.configurations.GuestConfig;
import com.victoricare.api.dtos.inputs.LogInInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.entities.LogIn;
import com.victoricare.api.entities.User;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import com.victoricare.api.security.services.UserDetailsImpl;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LogInServiceTest {
    private final String JWT_TEST = "JWT_TEST";
    private static final String EMAIL = "test@email.fr";
    private static final String USERNAME = "TEST31";
    private static final String PASS = "Test31@";
    private static final String BROWSER = "Firefox";
    private static final String IP = "127.0.0.1";
    private static final boolean REMEMBER_ME = true;
    private static final Integer USER_ID = 1;
    private static final Long ID = 1L;

    @Mock
    private ILogInRepository repository;

    @Mock
    private JWTToken jwtToken;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetailsImpl userDetails;

    @Mock
    private GuestConfig guestConfig;

    @Mock
    private HttpHeaderWrapper httpHeader;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private IUserService userService;

    @InjectMocks
    private LogInService service;

    private LogIn logIn;
    private LogInInputDTO logInInputDTO;
    private User user;
    private User guest = User.builder().pseudo("GUEST_TEST").build();

    private Map<String, Object> token = new HashMap<String, Object>();

    @BeforeEach
    public void setup() {
        this.initUser();
        this.initLogIn();
        this.initInput();
        this.initToken();
    }

    private void initToken() {
        token.put("expireAt", new Date());
        token.put("jwt", "testjwt");
        token.put("browser", "testbrowser");
        token.put("ip", "testip");
    }

    private void initUser() {
        this.user = User.builder()
                .id(USER_ID)
                .email(EMAIL)
                .pseudo(USERNAME)
                .rights("USER")
                .roles("USER")
                .build();
    }

    private void initLogIn() {
        this.logIn = LogIn.builder()
                .id(ID)
                .email(EMAIL)
                .pseudo(USERNAME)
                .browser(BROWSER)
                .ip(IP)
                .build();
    }

    private void initInput() {
        logInInputDTO = new LogInInputDTO();
        logInInputDTO.setIdentifier(EMAIL);
        logInInputDTO.setPass(PASS);
        logInInputDTO.setRemember(REMEMBER_ME);
    }

    @Test
    @Order(1)
    public void saveLogInMustBeOKTest() {
        // precondition
        this.userDetails = UserDetailsImpl.build(user);

        given(this.authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .willReturn(authentication);
        given(this.authentication.getPrincipal()).willReturn(userDetails);

        given(this.jwtToken.generate(USERNAME)).willReturn(token);

        given(repository.save(Mockito.any(LogIn.class))).willReturn(logIn);

        // action
        LogIn savedLogIn = service.doCreate(authenticationManager, jwtToken, logInInputDTO);

        // verify the output
        assertThat(savedLogIn.getCreationDate()).isNotNull();
        assertThat(savedLogIn.getCreationDate()).isNotNull();
    }

    @Test
    @Order(2)
    public void doLogOutMustBeOKTest() {
        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(logIn)); // User exists
        given(repository.save(logIn)).willReturn(logIn);
        this.service.doLogOut(user, ID);
        assertThat(logIn.getDeletionDate()).isNotNull();
    }

    @Test
    @Order(3)
    public void doDeleteMustBeOKTest() {
        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(logIn)); // User exists

        willDoNothing().given(repository).delete(logIn);

        assertDoesNotThrow(() -> this.service.doDelete(user, ID));
    }

    @Test
    @Order(3)
    public void doCreateTokenMustBeOKTest() {
        // precondition
        given(guestConfig.getUser()).willReturn(this.user); // User exists
        given(this.jwtToken.generate(USERNAME)).willReturn(token);
        given(this.jwtToken.getJWT(token)).willReturn(JWT_TEST);
        String jwt = this.service.doCreateToken(this.jwtToken);

        assertThat(jwt).isEqualTo(JWT_TEST);
    }

    @Test
    @Order(3)
    public void doRefreshTokenMustBeOKTest() {
        String oldJWTTest = "OLD_JWT_TEST";
        this.httpHeader.setJwt(oldJWTTest);
        // precondition
        given(guestConfig.getUser()).willReturn(guest); // User exists
        given(this.jwtToken.getHttpHeader()).willReturn(this.httpHeader);
        given(this.jwtToken.getHttpHeader().getJwt()).willReturn(oldJWTTest);
        given(this.jwtToken.generate(USERNAME)).willReturn(token);
        given(repository.findByPseudoAndToken(Mockito.anyString(), Mockito.anyString())).willReturn(List.of(logIn));
        given(repository.save(Mockito.any(LogIn.class))).willReturn(logIn);
        given(this.jwtToken.getJWT(token)).willReturn(JWT_TEST);

        String jwt = this.service.doRefreshToken(user, jwtToken);

        assertThat(jwt).isEqualTo(JWT_TEST);
    }

    @Test
    @Order(3)
    public void doRefreshTokeWithNonExistingLoginMustThrtowExceptionTest() {
        String oldJWTTest = "OLD_JWT_TEST";
        this.httpHeader.setJwt(oldJWTTest);
        // precondition
        given(guestConfig.getUser()).willReturn(guest); // User exists
        given(this.jwtToken.getHttpHeader()).willReturn(this.httpHeader);
        given(this.jwtToken.getHttpHeader().getJwt()).willReturn(oldJWTTest);
        given(this.jwtToken.generate(USERNAME)).willReturn(token);
        given(repository.findByPseudoAndToken(Mockito.anyString(), Mockito.anyString())).willReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () -> this.service.doRefreshToken(user, jwtToken));
    }

    @Order(2)
    public void doGetMustBeOKTest() {
        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(logIn));

        // action
        LogIn existingLogIn = service.doGet(ID);

        // verify
        assertThat(existingLogIn).isNotNull();
    }

    @Test
    @Order(2)
    public void doGetNonExistingLogInMustThrowExceptionTest() {
        // precondition
        given(repository.findById(ID)).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.doGet(ID));
    }

    @Test
    @Order(10)
    public void getPageTest() {
        PageInputDTO page = new PageInputDTO();
        Pageable pageable = PageValidator.getPageable(LogIn.class, page);
        Page<LogIn> logInPage = new PageImpl<LogIn>(List.of(logIn));

        // precondition
        given(repository.findAll(pageable)).willReturn(logInPage);

        // action
        Page<LogIn> logInList = service.doPage(pageable);

        // verify
        assertThat(logInList).isNotNull();
        assertThat(logInList.getSize()).isEqualTo(1);
        assertThat(logInList.getContent().get(0).equals(logIn));
    }
}