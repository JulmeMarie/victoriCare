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
import com.victoricare.api.services.impl.BanishmentService;
import com.victoricare.api.validators.impl.PageValidator;
import com.victoricare.api.repositories.IBanishmentRepository;
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
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.entities.Banishment;
import com.victoricare.api.entities.User;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("BanishmentService Tests")
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BanishmentServiceTest {
    private static final Integer USER_ID = 1;
    private static final Integer ID = 1;

    @Mock
    private JWTToken jwtToken;

    @Mock
    private IBanishmentRepository repository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserService userService;

    @InjectMocks
    private BanishmentService service;

    private Banishment banishment;

    private User user = User.builder().id(USER_ID).build();

    @BeforeEach
    public void setup() {
        banishment = Banishment.builder()
                .id(ID)
                .build();
    }

    @Test
    @Order(1)
    public void shouldCreateBanishmentSuccessfully() {
        // precondition
        given(repository.save(Mockito.any(Banishment.class))).willReturn(banishment);

        // action
        assertDoesNotThrow(() -> service.doCreate(this.jwtToken));
    }

    @Test
    @Order(2)
    public void getBanishmentByIdTest() {
        banishment.setId(ID);
        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(banishment));

        // action
        Banishment existingBanishment = service.doGet(ID);

        // verify
        assertThat(existingBanishment).isNotNull();
    }

    @Test
    @Order(2)
    public void getBanishmentByIdThrowExceptionTest() {
        banishment.setId(ID);
        // precondition
        given(repository.findById(ID)).willReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.doGet(ID));
    }

    @Test
    @Order(2)
    public void dotCanceledBanishmentByIdThrowException() {
        banishment.setDeletionDate(new Date());
        // precondition
        given(repository.findById(banishment.getId())).willReturn(Optional.of(banishment));
        assertThrows(ActionNotAllowedException.class, () -> service.doCancel(user, banishment.getId()));
    }

    @Test
    @Order(3)
    public void getPageTest() {
        PageInputDTO page = new PageInputDTO();
        Pageable pageable = PageValidator.getPageable(Banishment.class, page);
        Page<Banishment> banishmentPage = new PageImpl<Banishment>(List.of(banishment));

        // precondition
        given(repository.findAll(pageable)).willReturn(banishmentPage);

        // action
        Page<Banishment> banishmentList = service.doPage(pageable);

        // verify
        assertThat(banishmentList).isNotNull();
        assertThat(banishmentList.getSize()).isEqualTo(1);
        assertThat(banishmentList.getContent().get(0).equals(banishment));

    }

    @Test
    @Order(7)
    public void cancelBanishmentTest() {

        given(repository.findById(banishment.getId())).willReturn(Optional.of(banishment));

        // precondition
        given(repository.save(banishment)).willReturn(banishment);
        // willDoNothing().given(repository).delete(banishment);

        // action
        service.doCancel(user, banishment.getId());

        // verify
        assertThat(banishment.getDeletionDate()).isNotNull();
    }

    @Test
    @Order(8)
    public void cancelDeletedBanishmentThrowException() {
        banishment.setDeletionDate(new Date());
        // precondition
        given(repository.findById(banishment.getId())).willReturn(Optional.of(banishment));
        assertThrows(ActionNotAllowedException.class, () -> service.doCancel(user, banishment.getId()));
    }

    @Test
    @Order(9)
    public void deleteBanishmentTest() {

        given(repository.findById(banishment.getId())).willReturn(Optional.of(banishment));

        // precondition
        willDoNothing().given(repository).delete(banishment);

        // action
        service.doDelete(user, banishment.getId());

        // verify
        verify(repository, times(1)).delete(banishment);
    }
}