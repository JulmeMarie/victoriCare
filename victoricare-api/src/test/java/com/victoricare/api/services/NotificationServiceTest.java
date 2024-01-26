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
import com.victoricare.api.services.impl.NotificationService;
import com.victoricare.api.services.impl.CodeService;
import com.victoricare.api.validators.impl.PageValidator;
import com.victoricare.api.repositories.INotificationRepository;
import com.victoricare.api.repositories.IUserRepository;
import com.victoricare.api.security.JWTToken;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import com.victoricare.api.dtos.inputs.NotificationInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.entities.Notification;
import com.victoricare.api.entities.User;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NotificationServiceTest {
    private static final String NAME = "TASKNAME_TEST";
    private static final Integer USER_ID = 1;
    private static final Integer FAMILY_ID = 1;
    private static final Long ID = 1L;

    @Mock
    private INotificationRepository repository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private CodeService codeService;

    @Mock
    private JWTToken jwtToken;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private NotificationService service;

    private Notification notification;

    private NotificationInputDTO notificationInputDTO;
    private User onlineUser = User.builder().id(USER_ID).build();

    @BeforeEach
    public void setup() {
        this.notification = Notification.builder().id(ID).build();
        this.initInput();

    }

    private void initInput() {
        notificationInputDTO = new NotificationInputDTO();
    }

    @Test
    @Order(1)
    public void doCreateNotificationMustBeOKTest() {
        given(repository.save(Mockito.any(Notification.class))).willReturn(notification);

        // action
        Notification savedNotification = service.doCreate(notificationInputDTO);

        // verify the output
        assertThat(savedNotification.getCreationDate()).isNotNull();
    }

    @Test
    @Order(2)
    public void doViewNotificationMustBeOKTest() {

        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(this.notification));
        given(repository.save(Mockito.any(Notification.class))).willReturn(this.notification);

        // action
        Notification savedNotification = service.doView(this.onlineUser, ID);

        // verify the output
        assertThat(savedNotification.getViewDate()).isNotNull();
    }

    @Test
    @Order(2)
    public void doReadingNotificationMustBeOKTest() {

        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(this.notification));
        given(repository.save(Mockito.any(Notification.class))).willReturn(this.notification);

        // action
        Notification savedNotification = service.doRead(this.onlineUser, ID);

        // verify the output
        assertThat(savedNotification.getReadingDate()).isNotNull();
    }

    @Test
    @Order(4)
    public void doGetNonExistingNotificationMustThrowExceptionTest() {
        // precondition
        given(repository.findById(ID)).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.doGet(this.onlineUser, ID));
    }

    @Test
    @Order(4)
    public void doDeleteNotificationCareTaskMustBeOKTest() {
        // precondition
        given(repository.findById(notification.getId())).willReturn(Optional.of(notification));

        willDoNothing().given(repository).delete(notification);
        service.doDelete(onlineUser, notification.getId());
    }

    @Test
    @Order(4)
    public void getPageTest() {
        PageInputDTO page = new PageInputDTO();
        Pageable pageable = PageValidator.getPageable(Notification.class, page);
        Page<Notification> notificationPage = new PageImpl<Notification>(List.of(notification));

        // precondition
        given(repository.findAll(pageable)).willReturn(notificationPage);

        // action
        Page<Notification> notificationList = service.doPage(pageable);

        // verify
        System.out.println(notificationList);
        assertThat(notificationList).isNotNull();
        assertThat(notificationList.getSize()).isEqualTo(1);
        assertThat(notificationList.getContent().get(0).equals(notification));
    }

    @Test
    @Order(5)
    public void doListByUserMustBeOk() {
        // precondition
        given(repository.findAllByUser(USER_ID)).willReturn(List.of(notification));
        List<Notification> notificationren = service.doListByUser(onlineUser, USER_ID);
        // verify
        assertThat(notificationren).isNotNull();
        assertThat(notificationren.size()).isEqualTo(1);
        assertThat(notificationren.get(0).equals(notification));

    }

    @Test
    @Order(5)
    public void doListByBabySitterMustBeOk() {
        Long BABY_SITTER_ID = 2L;
        // precondition
        given(repository.findAllByBabySitter(BABY_SITTER_ID)).willReturn(List.of(notification));
        List<Notification> notificationren = service.doListByBabySitter(onlineUser, BABY_SITTER_ID);
        // verify
        assertThat(notificationren).isNotNull();
        assertThat(notificationren.size()).isEqualTo(1);
        assertThat(notificationren.get(0).equals(notification));

    }
}