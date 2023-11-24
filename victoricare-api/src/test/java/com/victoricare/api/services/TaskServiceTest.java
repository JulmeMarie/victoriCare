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
import com.victoricare.api.services.impl.TaskService;
import com.victoricare.api.validators.impl.PageValidator;
import com.victoricare.api.repositories.ITaskRepository;
import com.victoricare.api.repositories.IUserRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import com.victoricare.api.dtos.inputs.TaskInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.entities.Task;
import com.victoricare.api.entities.User;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;

@DisplayName("TaskService Tests")
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskServiceTest {
    private static final String TEST_NAME = "123 Main St";
    private static final String TEST_TITLE = "New York";
    private static final String TEST_NATURE = "10001";
    private static final Integer USER_ID = 1;
    private static final Integer DOC_ID = 1;
    private static final Long ID = 1L;

    @Mock
    private ITaskRepository repository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserService userService;

    @InjectMocks
    private TaskService service;

    private Task task;
    private TaskInputDTO taskInputDTO;
    private User user = User.builder().id(USER_ID).build();

    @BeforeEach
    public void setup() {
        task = Task.builder()
                .id(ID)
                .build();

        taskInputDTO = new TaskInputDTO();
        taskInputDTO.setName(TEST_NAME);
    }

    @Test
    @Order(1)
    public void shouldCreateTaskSuccessfully() {
        // precondition
        given(repository.save(Mockito.any(Task.class))).willReturn(task);

        // action
        Task savedTask = service.doCreate(user, taskInputDTO);

        // verify the output
        assertThat(savedTask.getCreationDate()).isNotNull(); // Verify creation date
        assertThat(savedTask.getCreationAuthorId()).isEqualTo(user.getId()); // Creation author must
        // match the user
    }

    @Test
    @Order(2)
    public void getTaskByIdTest() {
        task.setId(ID);
        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(task));

        // action
        Task existingTask = service.doGet(user, ID);

        // verify
        assertThat(existingTask).isNotNull();

    }

    @Test
    @Order(2)
    public void dotCanceledTaskByIdThrowException() {
        task.setDeletionDate(new Date());
        // precondition
        given(repository.findById(task.getId())).willReturn(Optional.of(task));
        assertThrows(ActionNotAllowedException.class, () -> service.doCancel(user, task.getId()));
    }

    @Test
    @Order(2)
    public void getDeletedTaskByIdThrowException() {
        // precondition
        given(repository.findById(task.getId())).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.doGet(user, task.getId()));
    }

    @Test
    @Order(3)
    public void getPageTest() {
        PageInputDTO page = new PageInputDTO();
        Pageable pageable = PageValidator.getPageable(Task.class, page);
        Page<Task> taskPage = new PageImpl<Task>(List.of(task));

        // precondition
        given(repository.findAll(pageable)).willReturn(taskPage);

        // action
        Page<Task> taskList = service.doPage(pageable);

        // verify
        assertThat(taskList).isNotNull();
        assertThat(taskList.getSize()).isEqualTo(1);
        assertThat(taskList.getContent().get(0).equals(task));

    }

    @Test
    @Order(5)
    public void updateTask() {
        // precondition
        given(repository.findById(task.getId())).willReturn(Optional.of(task));
        given(repository.save(task)).willReturn(task);
        taskInputDTO.setName("UPDATED_NAME");

        // action
        Task updatedTask = service.doUpdate(user, taskInputDTO, task.getId());

        // verify
        System.out.println(updatedTask);
        assertThat(updatedTask.getName()).isEqualTo("UPDATED_NAME");
        assertThat(updatedTask.getUpdateDate()).isNotNull();
    }

    @Test
    @Order(6)
    public void updateDeletedTaskThrowException() {
        task.setDeletionDate(new Date());
        // precondition
        given(repository.findById(task.getId())).willReturn(Optional.of(task));
        assertThrows(ActionNotAllowedException.class, () -> service.doUpdate(user, taskInputDTO, task.getId()));
    }

    @Test
    @Order(7)
    public void cancelTaskTest() {

        given(repository.findById(task.getId())).willReturn(Optional.of(task));

        // precondition
        given(repository.save(task)).willReturn(task);
        // willDoNothing().given(repository).delete(task);

        // action
        service.doCancel(user, task.getId());

        // verify
        assertThat(task.getDeletionDate()).isNotNull();
    }

    @Test
    @Order(8)
    public void cancelDeletedTaskThrowException() {
        task.setDeletionDate(new Date());
        // precondition
        given(repository.findById(task.getId())).willReturn(Optional.of(task));
        assertThrows(ActionNotAllowedException.class, () -> service.doCancel(user, task.getId()));
    }

    @Test
    @Order(9)
    public void deleteTaskTest() {

        given(repository.findById(task.getId())).willReturn(Optional.of(task));

        // precondition
        willDoNothing().given(repository).delete(task);

        // action
        service.doDelete(user, task.getId());

        // verify
        verify(repository, times(1)).delete(task);
    }

    @Test
    @Order(4)
    public void getListByUserTest() {
        // precondition
        given(repository.findAllByUser(USER_ID)).willReturn(List.of(task));

        // action
        List<Task> taskList = service.doListByUser(user, USER_ID);

        // verify
        assertThat(taskList).isNotNull();
        assertThat(taskList.size()).isEqualTo(1);
        assertThat(taskList.get(0).equals(task));
    }
}