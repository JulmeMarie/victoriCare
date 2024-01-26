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
import com.victoricare.api.services.impl.FamilyService;
import com.victoricare.api.services.impl.ChildCareService;
import com.victoricare.api.services.impl.ChildCareTaskService;
import com.victoricare.api.validators.impl.PageValidator;
import com.victoricare.api.repositories.IChildCareTaskRepository;
import com.victoricare.api.repositories.IUserRepository;
import com.victoricare.api.security.JWTToken;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import com.victoricare.api.dtos.inputs.ChildCareTaskInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.entities.BabySitter;
import com.victoricare.api.entities.ChildCare;
import com.victoricare.api.entities.ChildCareTask;
import com.victoricare.api.entities.Family;
import com.victoricare.api.entities.User;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ChildCareTaskServiceTest {
    private static final String NAME = "TASKNAME_TEST";
    private static final String DESCRIPTION = "TASKNAME_DESCRIPTION";
    private static final Integer USER_ID = 1;
    private static final Integer FAMILY_ID = 1;
    private static final Integer CHILD_ID = 1;
    private static final Long CHILDCARE_ID = 1L;
    private static final Long ID = 1L;

    @Mock
    private IChildCareTaskRepository repository;

    @Mock
    private ChildCareService childCareService;

    @Mock
    private FamilyService familyService;

    @Mock
    private JWTToken jwtToken;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private ChildCareTaskService service;

    private ChildCare childCare;

    private Family family;

    private ChildCareTask childCareTask;
    private ChildCareTaskInputDTO childCareTaskInputDTO;
    private User onlineUser = User.builder().id(USER_ID).build();

    @BeforeEach
    public void setup() {
        this.family = Family.builder().id(FAMILY_ID).build();
        this.childCare = ChildCare.builder().id(CHILDCARE_ID).creationFamilyId(FAMILY_ID).build();
        this.initChildCareTask();
        this.initInput();
    }

    private void initChildCareTask() {
        this.childCareTask = ChildCareTask.builder()
                .id(ID)
                .taskName(NAME)
                .taskDescription(DESCRIPTION)
                .childId(CHILD_ID)
                .childCare(this.childCare)
                .build();
    }

    private void initInput() {
        childCareTaskInputDTO = new ChildCareTaskInputDTO();
        childCareTaskInputDTO.setTaskName(NAME);
        childCareTaskInputDTO.setTaskDescription(DESCRIPTION);
        childCareTaskInputDTO.setChildId(CHILD_ID);
        childCareTaskInputDTO.setChildCareId(CHILDCARE_ID);
    }

    @Test
    @Order(1)
    public void doCreateChildCareTaskMustBeOKTest() {

        this.childCare.setBabySitter(BabySitter.builder().userId(USER_ID).build());
        // precondition
        given(childCareService.doGet(onlineUser, ID)).willReturn(this.childCare);
        given(familyService.doGet(onlineUser, childCare.getCreationFamilyId())).willReturn(this.family);
        // given(service.isAResponsible(user.getId(), family,
        // childCare)).willReturn(true);
        given(repository.save(Mockito.any(ChildCareTask.class))).willReturn(childCareTask);

        // action
        ChildCareTask savedChildCareTask = service.doCreate(this.onlineUser, childCareTaskInputDTO);

        // verify the output
        assertThat(savedChildCareTask.getCreationDate()).isNotNull();
    }

    @Test
    @Order(1)
    public void doCreateChildCareTaskNotResponsibleMustMustThrowExceptionTest() {
        // precondition
        this.childCare.setBabySitter(null);
        given(childCareService.doGet(onlineUser, ID)).willReturn(this.childCare);
        given(familyService.doGet(onlineUser, childCare.getCreationFamilyId())).willReturn(this.family);
        assertThrows(ActionNotAllowedException.class, () -> service.doCreate(this.onlineUser, childCareTaskInputDTO));
    }

    @Test
    @Order(2)
    public void doUpdateChildCareTaskMustBeOKTest() {
        this.childCare.setBabySitter(BabySitter.builder().userId(USER_ID).build());
        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(this.childCareTask));
        given(childCareService.doGet(onlineUser, ID)).willReturn(this.childCare);
        given(familyService.doGet(onlineUser, FAMILY_ID)).willReturn(this.family);
        // given(ChildCareService.isBabySitter(user.getId(),
        // childCare)).willReturn(true);
        given(repository.save(Mockito.any(ChildCareTask.class))).willReturn(this.childCareTask);

        // action
        ChildCareTask savedChildCareTask = service.doUpdate(this.onlineUser, childCareTaskInputDTO, ID);

        // verify the output
        assertThat(savedChildCareTask.getAupdateDate()).isNotNull();
    }

    @Test
    @Order(3)
    public void doUpdateChildCareTaskNotResponsibleMustMustThrowExceptionTest() {
        // precondition

        this.childCare.setBabySitter(null);
        given(repository.findById(ID)).willReturn(Optional.of(this.childCareTask));
        given(childCareService.doGet(onlineUser, ID)).willReturn(this.childCare);
        given(familyService.doGet(onlineUser, childCare.getCreationFamilyId())).willReturn(this.family);
        assertThrows(ActionNotAllowedException.class,
                () -> service.doUpdate(this.onlineUser, childCareTaskInputDTO, ID));
    }

    @Test
    @Order(3)
    public void doCancelChildCareTaskMustBeOKTest() {
        this.family.setCreationAuthorId(FAMILY_ID);
        given(repository.findById(childCareTask.getId())).willReturn(Optional.of(childCareTask));
        given(familyService.doGet(onlineUser, FAMILY_ID)).willReturn(this.family);
        // given(ChildCareService.isBabySitter(user.getId(),
        // childCare)).willReturn(true);
        given(repository.save(childCareTask)).willReturn(childCareTask);

        service.doCancel(onlineUser, childCareTask.getId());

        assertThat(childCareTask.getDeletionDate()).isNotNull();
    }

    @Test
    @Order(3)
    public void doCancelChildCareTaskNotResponsibleTest() {
        // this.family.setCreationAuthorId(FAMILY_ID);//No responsible
        given(repository.findById(childCareTask.getId())).willReturn(Optional.of(childCareTask));
        given(familyService.doGet(onlineUser, FAMILY_ID)).willReturn(this.family);
        assertThrows(ActionNotAllowedException.class, () -> service.doCancel(onlineUser, childCareTask.getId()));
    }

    @Test
    @Order(4)
    public void doDeleteChildCareTaskMustBeOKTest() {
        // precondition
        given(repository.findById(childCareTask.getId())).willReturn(Optional.of(childCareTask));

        willDoNothing().given(repository).delete(childCareTask);
        service.doDelete(onlineUser, childCareTask.getId());
    }

    @Test

    @Order(3)
    public void getPageTest() {
        PageInputDTO page = new PageInputDTO();
        Pageable pageable = PageValidator.getPageable(ChildCareTask.class, page);
        Page<ChildCareTask> childCareTaskPage = new PageImpl<ChildCareTask>(List.of(childCareTask));

        // precondition
        given(repository.findAll(pageable)).willReturn(childCareTaskPage);

        // action
        Page<ChildCareTask> childCareTaskList = service.doPage(pageable);

        // verify
        System.out.println(childCareTaskList);
        assertThat(childCareTaskList).isNotNull();
        assertThat(childCareTaskList.getSize()).isEqualTo(1);
        assertThat(childCareTaskList.getContent().get(0).equals(childCareTask));
    }

    @Test
    @Order(2)
    public void doGetMustBeOKTest() {
        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(childCareTask));

        // action
        ChildCareTask existingChildCareTask = service.doGet(onlineUser, ID);

        // verify
        assertThat(existingChildCareTask).isNotNull();
    }

    @Test
    @Order(5)
    public void doGetUnExistingTest() {
        // precondition
        given(repository.findById(ID)).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.doGet(onlineUser, ID));
    }

    @Test
    @Order(2)
    public void doListMustBeOKTest() {
        // precondition
        given(repository.findAllByChildCare(CHILDCARE_ID)).willReturn(List.of(this.childCareTask));
        List<ChildCareTask> childCareTasks = service.doListByChildCare(onlineUser, CHILDCARE_ID);

        // verify
        assertThat(childCareTasks.size()).isEqualTo(1);
        assertThat(childCareTasks.get(0).equals(childCareTask));
    }
}