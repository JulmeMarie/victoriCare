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
import com.victoricare.api.services.impl.SectionService;
import com.victoricare.api.validators.impl.PageValidator;
import com.victoricare.api.repositories.ISectionRepository;
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
import com.victoricare.api.dtos.inputs.SectionInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.entities.Section;
import com.victoricare.api.entities.User;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;

@DisplayName("SectionService Tests")
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SectionServiceTest {
    private static final String TEST_NAME = "123 Main St";
    private static final String TEST_TITLE = "New York";
    private static final String TEST_NATURE = "10001";
    private static final Integer USER_ID = 1;
    private static final Integer DOC_ID = 1;
    private static final Integer ID = 1;

    @Mock
    private IDocService docService;

    @Mock
    private ISectionRepository repository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserService userService;

    @InjectMocks
    private SectionService service;

    private Section section;
    private SectionInputDTO sectionInputDTO;
    private User user = User.builder().id(USER_ID).build();

    @BeforeEach
    public void setup() {
        section = Section.builder()
                .id(ID)
                .build();

        sectionInputDTO = new SectionInputDTO();
        sectionInputDTO.setDocId(DOC_ID);
        sectionInputDTO.setTitle(TEST_TITLE);
    }

    @Test
    @Order(1)
    @DisplayName("For a given correct sectionInputDTO, " +
            "we should create and return successfully a new section with a valid creation date and a valid creation author ID")
    public void shouldCreateSectionSuccessfully() {
        // precondition
        given(repository.save(Mockito.any(Section.class))).willReturn(section);
        given(this.docService.doGet(user, DOC_ID)).willReturn(null);

        // action
        Section savedSection = service.doCreate(user, sectionInputDTO);

        // verify the output
        assertThat(savedSection.getCreationDate()).isNotNull(); // Verify creation date
        assertThat(savedSection.getCreationAuthorId()).isEqualTo(user.getId()); // Creation author must
                                                                                // match the user
    }

    @Test
    @Order(2)
    public void getSectionByIdTest() {
        section.setId(ID);
        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(section));

        // action
        Section existingSection = service.doGet(user, ID);

        // verify
        assertThat(existingSection).isNotNull();

    }

    @Test
    @Order(2)
    public void dotCanceledSectionByIdThrowException() {
        section.setDeletionDate(new Date());
        // precondition
        given(repository.findById(section.getId())).willReturn(Optional.of(section));
        assertThrows(ResourceNotFoundException.class, () -> service.doCancel(user, section.getId()));
    }

    @Test
    @Order(2)
    public void getDeletedSectionByIdThrowException() {
        // precondition
        given(repository.findById(section.getId())).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.doGet(user, section.getId()));
    }

    @Test
    @Order(3)
    public void getPageTest() {
        PageInputDTO page = new PageInputDTO();
        Pageable pageable = PageValidator.getPageable(Section.class, page);
        Page<Section> sectionPage = new PageImpl<Section>(List.of(section));

        // precondition
        given(repository.findAll(pageable)).willReturn(sectionPage);

        // action
        Page<Section> sectionList = service.doPage(pageable);

        // verify
        assertThat(sectionList).isNotNull();
        assertThat(sectionList.getSize()).isEqualTo(1);
        assertThat(sectionList.getContent().get(0).equals(section));

    }

    @Test
    @Order(4)
    public void getListByDocTest() {
        // precondition
        given(repository.findAllByDoc(DOC_ID)).willReturn(List.of(section));

        // action
        List<Section> sectionList = service.doListByDoc(user, DOC_ID);

        // verify
        assertThat(sectionList).isNotNull();
        assertThat(sectionList.size()).isEqualTo(1);
        assertThat(sectionList.get(0).equals(section));
    }

    @Test
    @Order(5)
    public void updateSection() {
        section.setCreationAuthorId(user.getId());
        // precondition
        given(repository.findById(section.getId())).willReturn(Optional.of(section));
        given(repository.save(section)).willReturn(section);
        sectionInputDTO.setTitle("UPDATED_NAME");

        // action
        Section updatedSection = service.doUpdate(user, sectionInputDTO, section.getId());

        // verify
        System.out.println(updatedSection);
        assertThat(updatedSection.getTitle()).isEqualTo("UPDATED_NAME");
        assertThat(updatedSection.getUpdateDate()).isNotNull();
    }

    @Test
    @Order(6)
    public void updateSectionUnerNotAuthorThrowException() {
        Integer otherUserId = 2;
        section.setCreationAuthorId(otherUserId);
        // precondition
        given(repository.findById(section.getId())).willReturn(Optional.of(section));
        assertThrows(ActionNotAllowedException.class, () -> service.doUpdate(user, sectionInputDTO, section.getId()));
    }

    @Test
    @Order(6)
    public void updateDeletedSectionThrowException() {
        section.setDeletionDate(new Date());
        // precondition
        given(repository.findById(section.getId())).willReturn(Optional.of(section));
        assertThrows(ResourceNotFoundException.class, () -> service.doUpdate(user, sectionInputDTO, section.getId()));
    }

    @Test
    @Order(7)
    public void cancelSectionTest() {
        section.setCreationAuthorId(user.getId());
        given(repository.findById(section.getId())).willReturn(Optional.of(section));

        // precondition
        given(repository.save(section)).willReturn(section);
        // willDoNothing().given(repository).delete(section);

        // action
        service.doCancel(user, section.getId());

        // verify
        assertThat(section.getDeletionDate()).isNotNull();
    }

    @Test
    @Order(8)
    public void cancelSectionThrowException() {
        Integer otherUserId = 2;
        section.setCreationAuthorId(otherUserId);
        // precondition
        given(repository.findById(section.getId())).willReturn(Optional.of(section));
        assertThrows(ActionNotAllowedException.class, () -> service.doCancel(user, section.getId()));
    }

    @Test
    @Order(9)
    public void deleteSectionTest() {

        given(repository.findById(section.getId())).willReturn(Optional.of(section));
        // precondition
        willDoNothing().given(repository).delete(section);

        // action
        service.doDelete(user, section.getId());

        // verify
        verify(repository, times(1)).delete(section);
    }
}