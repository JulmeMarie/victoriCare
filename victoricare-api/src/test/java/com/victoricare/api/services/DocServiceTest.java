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
import com.victoricare.api.services.impl.DocService;
import com.victoricare.api.validators.impl.PageValidator;
import com.victoricare.api.repositories.IDocRepository;
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
import com.victoricare.api.dtos.inputs.DocInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.entities.Contact;
import com.victoricare.api.entities.Doc;
import com.victoricare.api.entities.User;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;

@DisplayName("DocService Tests")
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DocServiceTest {
    private static final String TEST_NAME = "123 Main St";
    private static final String TEST_TITLE = "New York";
    private static final String TEST_NATURE = "10001";
    private static final Integer USER_ID = 1;
    private static final Integer DOC_ID = 1;
    private static final Integer ID = 1;

    @Mock
    private IDocRepository repository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserService userService;

    @InjectMocks
    private DocService service;

    private Doc doc;
    private DocInputDTO docInputDTO;
    private User user = User.builder().id(USER_ID).build();

    @BeforeEach
    public void setup() {
        doc = Doc.builder()
                .id(ID)
                .build();

        docInputDTO = new DocInputDTO();
        docInputDTO.setTitle(TEST_TITLE);
    }

    @Test
    @Order(1)
    @DisplayName("For a given correct docInputDTO, " +
            "we should create and return successfully a new doc with a valid creation date and a valid creation author ID")
    public void shouldCreateDocSuccessfully() {
        // precondition
        given(repository.save(Mockito.any(Doc.class))).willReturn(doc);

        // action
        Doc savedDoc = service.doCreate(user, docInputDTO);

        // verify the output
        assertThat(savedDoc.getCreationDate()).isNotNull(); // Verify creation date
        assertThat(savedDoc.getCreationAuthor().getId()).isEqualTo(user.getId()); // Creation author must
        // match the user
    }

    @Test
    @Order(2)
    public void getDocByIdTest() {
        doc.setId(ID);
        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(doc));

        // action
        Doc existingDoc = service.doGet(user, ID);

        // verify
        assertThat(existingDoc).isNotNull();

    }

    @Test
    @Order(2)
    public void dotCanceledDocByIdThrowException() {
        doc.setDeletionDate(new Date());
        // precondition
        given(repository.findById(doc.getId())).willReturn(Optional.of(doc));
        assertThrows(ActionNotAllowedException.class, () -> service.doCancel(user, doc.getId()));
    }

    @Test
    @Order(2)
    public void getDeletedDocByIdThrowException() {
        // precondition
        given(repository.findById(doc.getId())).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.doGet(user, doc.getId()));
    }

    @Test
    @Order(3)
    public void getPageTest() {
        PageInputDTO page = new PageInputDTO();
        Pageable pageable = PageValidator.getPageable(Doc.class, page);
        Page<Doc> docPage = new PageImpl<Doc>(List.of(doc));

        // precondition
        given(repository.findAll(pageable)).willReturn(docPage);

        // action
        Page<Doc> docList = service.doPage(pageable);

        // verify
        assertThat(docList).isNotNull();
        assertThat(docList.getSize()).isEqualTo(1);
        assertThat(docList.getContent().get(0).equals(doc));

    }

    @Test
    @Order(5)
    public void updateDoc() {
        // precondition
        given(repository.findById(doc.getId())).willReturn(Optional.of(doc));
        given(repository.save(doc)).willReturn(doc);
        docInputDTO.setTitle("UPDATED_NAME");

        // action
        Doc updatedDoc = service.doUpdate(user, docInputDTO, doc.getId());

        // verify
        System.out.println(updatedDoc);
        assertThat(updatedDoc.getTitle()).isEqualTo("UPDATED_NAME");
        assertThat(updatedDoc.getUpdateDate()).isNotNull();
    }

    @Test
    @Order(6)
    public void updateDeletedDocThrowException() {
        doc.setDeletionDate(new Date());
        // precondition
        given(repository.findById(doc.getId())).willReturn(Optional.of(doc));
        assertThrows(ActionNotAllowedException.class, () -> service.doUpdate(user, docInputDTO, doc.getId()));
    }

    @Test
    @Order(7)
    public void cancelDocTest() {

        given(repository.findById(doc.getId())).willReturn(Optional.of(doc));

        // precondition
        given(repository.save(doc)).willReturn(doc);
        // willDoNothing().given(repository).delete(doc);

        // action
        service.doCancel(user, doc.getId());

        // verify
        assertThat(doc.getDeletionDate()).isNotNull();
    }

    @Test
    @Order(8)
    public void cancelDeletedDocThrowException() {
        doc.setDeletionDate(new Date());
        // precondition
        given(repository.findById(doc.getId())).willReturn(Optional.of(doc));
        assertThrows(ActionNotAllowedException.class, () -> service.doCancel(user, doc.getId()));
    }

    @Test
    @Order(9)
    public void deleteDocTest() {

        given(repository.findById(doc.getId())).willReturn(Optional.of(doc));

        // precondition
        willDoNothing().given(repository).delete(doc);

        // action
        service.doDelete(user, doc.getId());

        // verify
        verify(repository, times(1)).delete(doc);
    }

    @Test
    @Order(4)
    public void getListByUserTest() {
        // precondition
        given(repository.findAllByUser(USER_ID)).willReturn(List.of(doc));

        // action
        List<Doc> contactList = service.doListByUser(user, ID);

        // verify
        assertThat(contactList).isNotNull();
        assertThat(contactList.size()).isEqualTo(1);
        assertThat(contactList.get(0).equals(doc));

    }
}