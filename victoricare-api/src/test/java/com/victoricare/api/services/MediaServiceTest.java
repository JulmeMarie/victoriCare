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
import org.springframework.web.multipart.MultipartFile;
import com.victoricare.api.services.impl.FileService.PropKey;
import com.victoricare.api.services.impl.MediaService;
import com.victoricare.api.validators.impl.PageValidator;
import com.victoricare.api.repositories.IMediaRepository;
import com.victoricare.api.repositories.IUserRepository;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import com.victoricare.api.dtos.inputs.MediaInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.entities.Media;
import com.victoricare.api.entities.User;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;

@DisplayName("MediaService Tests")
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MediaServiceTest {
    private static final String TEST_NAME = "123 Main St";
    private static final String TEST_TITLE = "New York";
    private static final String TEST_TYPE = "10001";
    private static final Integer USER_ID = 1;
    private static final Integer SECTION_ID = 1;
    private static final Integer ID = 1;

    @Mock
    private IFileService fileService;

    @Mock
    private ISectionService sectionService;

    @Mock
    private IMediaRepository repository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserService userService;

    @InjectMocks
    private MediaService service;

    private Media media;
    private MediaInputDTO mediaInputDTO;
    private User user = User.builder().id(USER_ID).build();

    private Map<PropKey, Object> properties = new HashMap<PropKey, Object>();

    @BeforeEach
    public void setup() {
        media = Media.builder()
                .id(ID)
                .build();

        mediaInputDTO = new MediaInputDTO();
        mediaInputDTO.setName(TEST_NAME);
        mediaInputDTO.setType(TEST_TYPE);
        mediaInputDTO.setTitle(TEST_TITLE);
        mediaInputDTO.setSectionId(SECTION_ID);

        this.properties.put(PropKey.FILE_NAME, "TEST_FILE_NAME");
        this.properties.put(PropKey.FILE, new File("TEST"));
        this.properties.put(PropKey.FILE_EXTENTION, "JPEG");
        this.properties.put(PropKey.FOLDER_NAME, "IMAGE");
        try {
            MultipartFile mFile = Mockito.mock(MultipartFile.class);
            mediaInputDTO.setMFile(mFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    public void shouldCreateMediaSuccessfully() {
        // precondition
        given(this.fileService.doUpload(mediaInputDTO.getMFile(), mediaInputDTO.getType())).willReturn(properties);
        given(repository.save(Mockito.any(Media.class))).willReturn(media);
        given(this.sectionService.doGet(user, SECTION_ID)).willReturn(null);

        // action
        Media savedMedia = service.doCreate(user, mediaInputDTO);

        // verify the output
        assertThat(savedMedia.getCreationDate()).isNotNull(); // Verify creation date
        assertThat(savedMedia.getCreationAuthorId()).isEqualTo(user.getId()); // Creation author must
                                                                              // match the user
    }

    @Test
    @Order(2)
    public void getMediaByIdTest() {
        media.setId(ID);
        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(media));

        // action
        Media existingMedia = service.doGet(user, ID);

        // verify
        assertThat(existingMedia).isNotNull();
    }

    @Test
    @Order(2)
    public void dotCanceledMediaByIdThrowException() {
        media.setDeletionDate(new Date());
        // precondition
        given(repository.findById(media.getId())).willReturn(Optional.of(media));
        assertThrows(ResourceNotFoundException.class, () -> service.doCancel(user, media.getId()));
    }

    @Test
    @Order(2)
    public void getDeletedMediaByIdThrowException() {
        // precondition
        given(repository.findById(media.getId())).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.doGet(user, media.getId()));
    }

    @Test
    @Order(3)
    public void getPageTest() {
        PageInputDTO page = new PageInputDTO();
        Pageable pageable = PageValidator.getPageable(Media.class, page);
        Page<Media> mediaPage = new PageImpl<Media>(List.of(media));

        // precondition
        given(repository.findAll(pageable)).willReturn(mediaPage);

        // action
        Page<Media> mediaList = service.doPage(pageable);

        // verify
        assertThat(mediaList).isNotNull();
        assertThat(mediaList.getSize()).isEqualTo(1);
        assertThat(mediaList.getContent().get(0).equals(media));

    }

    @Test
    @Order(4)
    public void getListByUserTest() {
        // precondition
        given(repository.findAllByUserId(USER_ID)).willReturn(List.of(media));

        // action
        List<Media> mediaList = service.doListByUser(user, USER_ID);

        // verify
        assertThat(mediaList).isNotNull();
        assertThat(mediaList.size()).isEqualTo(1);
        assertThat(mediaList.get(0).equals(media));
    }

    @Test
    @Order(4)
    public void getListBySectionTest() {
        // precondition
        given(repository.findAllBySection(SECTION_ID)).willReturn(List.of(media));

        // action
        List<Media> mediaList = service.doListBySection(user, SECTION_ID);

        // verify
        assertThat(mediaList).isNotNull();
        assertThat(mediaList.size()).isEqualTo(1);
        assertThat(mediaList.get(0).equals(media));
    }

    @Test
    @Order(5)
    public void updateMedia() {
        media.setCreationAuthorId(user.getId());
        // precondition
        given(repository.findById(media.getId())).willReturn(Optional.of(media));
        given(repository.save(media)).willReturn(media);
        mediaInputDTO.setName("UPDATED_NAME");

        // action
        Media updatedMedia = service.doUpdate(user, mediaInputDTO, media.getId());

        // verify
        System.out.println(updatedMedia);
        assertThat(updatedMedia.getName()).isEqualTo("UPDATED_NAME");
        assertThat(updatedMedia.getUpdateDate()).isNotNull();
    }

    @Test
    @Order(6)
    public void updateMediaUnerNotAuthorThrowException() {
        Integer otherUserId = 2;
        media.setCreationAuthorId(otherUserId);
        // precondition
        given(repository.findById(media.getId())).willReturn(Optional.of(media));
        assertThrows(ActionNotAllowedException.class, () -> service.doUpdate(user, mediaInputDTO, media.getId()));
    }

    @Test
    @Order(6)
    public void updateDeletedMediaThrowException() {
        media.setDeletionDate(new Date());
        // precondition
        given(repository.findById(media.getId())).willReturn(Optional.of(media));
        assertThrows(ResourceNotFoundException.class, () -> service.doUpdate(user, mediaInputDTO, media.getId()));
    }

    @Test
    @Order(7)
    public void cancelMediaTest() {
        media.setCreationAuthorId(user.getId());
        given(repository.findById(media.getId())).willReturn(Optional.of(media));

        // precondition
        given(repository.save(media)).willReturn(media);
        // willDoNothing().given(repository).delete(media);

        // action
        service.doCancel(user, media.getId());

        // verify
        assertThat(media.getDeletionDate()).isNotNull();
    }

    @Test
    @Order(8)
    public void cancelMediaThrowException() {
        Integer otherUserId = 2;
        media.setCreationAuthorId(otherUserId);
        // precondition
        given(repository.findById(media.getId())).willReturn(Optional.of(media));
        assertThrows(ActionNotAllowedException.class, () -> service.doCancel(user, media.getId()));
    }

    @Test
    @Order(9)
    public void deleteMediaTest() {
        media.setFolder("test_folder");
        media.setName(TEST_NAME);

        given(repository.findById(media.getId())).willReturn(Optional.of(media));

        // precondition
        willDoNothing().given(repository).delete(media);
        willDoNothing().given(this.fileService).doDelete(Mockito.anyString(), Mockito.anyString());

        // action
        service.doDelete(user, media.getId());

        // verify
        verify(repository, times(1)).delete(media);
    }
}