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
import com.victoricare.api.services.impl.BabySitterService;
import com.victoricare.api.services.impl.CodeService;
import com.victoricare.api.validators.impl.PageValidator;
import com.victoricare.api.repositories.IBabySitterRepository;
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
import com.victoricare.api.dtos.inputs.BabySitterInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.entities.Code;
import com.victoricare.api.entities.BabySitter;
import com.victoricare.api.entities.Family;
import com.victoricare.api.entities.User;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BabySitterServiceTest {
    private static final String NAME = "TASKNAME_TEST";
    private static final Integer USER_ID = 1;
    private static final Integer FAMILY_ID = 1;
    private static final Long ID = 1L;

    @Mock
    private IBabySitterRepository repository;

    @Mock
    private CodeService codeService;

    @Mock
    private JWTToken jwtToken;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IFamilyService familyService;;

    @InjectMocks
    private BabySitterService service;

    private User onlineUser = User.builder().id(USER_ID).build();

    private BabySitter babySitter;

    private BabySitterInputDTO babySitterInputDTO;

    private Family family;

    @BeforeEach
    public void setup() {
        this.babySitter = BabySitter.builder()
                .id(ID)
                .firstname(NAME)
                .lastname("TEST")
                .build();

        this.family = Family.builder().id(FAMILY_ID).build();

        this.initInput();
    }

    private void initInput() {
        babySitterInputDTO = new BabySitterInputDTO();
        babySitterInputDTO.setFirstname(NAME);
        babySitterInputDTO.setFamilyId(FAMILY_ID);
        babySitterInputDTO.setLastname("TEST");
    }

    @Test
    @Order(1)
    public void doCreateBabySitterMustBeOKTest() {
        Code code = new Code();
        family.setCreationAuthorId(onlineUser.getId());
        // precondition
        given(this.familyService.doGet(this.onlineUser, family.getId())).willReturn(family);
        given(this.codeService.doCreate(CodeService.ECodeLength.SIX)).willReturn(code);
        given(repository.save(Mockito.any(BabySitter.class))).willReturn(babySitter);

        // action
        BabySitter savedBabySitter = service.doCreate(this.onlineUser, babySitterInputDTO);

        // verify the output
        assertThat(savedBabySitter.getCreationDate()).isNotNull();
    }

    @Test
    @Order(1)
    public void doCreateBabySitterMustThrowExceptionTest() {
        // precondition
        given(this.familyService.doGet(this.onlineUser, family.getId())).willReturn(family);
        assertThrows(ActionNotAllowedException.class, () -> service.doCreate(this.onlineUser, babySitterInputDTO));
    }

    @Test
    @Order(2)
    public void doUpdateBabySitterMustBeOKTest() {
        family.setCreationAuthorId(USER_ID);
        babySitter.setFamily(family);

        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(this.babySitter));
        given(repository.save(Mockito.any(BabySitter.class))).willReturn(this.babySitter);

        // action
        BabySitter savedBabySitter = service.doUpdate(this.onlineUser, babySitterInputDTO, ID);

        // verify the output
        assertThat(savedBabySitter.getUpdateDate()).isNotNull();
    }

    @Test
    @Order(3)
    public void doUpdateBabySitterMustThrowExceptionTest() {
        // precondition
        given(repository.findById(ID)).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.doUpdate(this.onlineUser, babySitterInputDTO, ID));
    }

    @Test
    @Order(3)
    public void doUpdateDeletedBabySitterMustThrowExceptionTest() {
        family.setCreationAuthorId(USER_ID);
        babySitter.setFamily(family);
        // precondition
        babySitter.setDeletionDate(new Date());
        given(repository.findById(ID)).willReturn(Optional.of(babySitter));
        assertThrows(ActionNotAllowedException.class, () -> service.doUpdate(this.onlineUser, babySitterInputDTO, ID));
    }

    @Test
    @Order(3)
    public void doUpdateBabySitterUserNotParentMustThrowExceptionTest() {
        // family.setCreationAuthorId(USER_ID);
        babySitter.setFamily(family);
        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(babySitter));
        assertThrows(ActionNotAllowedException.class, () -> service.doUpdate(this.onlineUser, babySitterInputDTO, ID));
    }

    @Test
    @Order(4)
    public void doGetNonExistingBabySitterMustThrowExceptionTest() {
        // precondition
        given(repository.findById(ID)).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.doGet(this.onlineUser.getId(), ID));
    }

    @Test
    @Order(4)
    public void doDeleteBabySitterCareTaskMustBeOKTest() {
        // precondition
        given(repository.findById(babySitter.getId())).willReturn(Optional.of(babySitter));
        willDoNothing().given(repository).delete(babySitter);
        service.doDelete(onlineUser, babySitter.getId());
    }

    @Test
    @Order(5)
    public void getPageTest() {
        PageInputDTO page = new PageInputDTO();
        Pageable pageable = PageValidator.getPageable(BabySitter.class, page);
        Page<BabySitter> babySitterPage = new PageImpl<BabySitter>(List.of(babySitter));

        // precondition
        given(repository.findAll(pageable)).willReturn(babySitterPage);

        // action
        Page<BabySitter> babySitterList = service.doPage(pageable);

        // verify
        System.out.println(babySitterList);
        assertThat(babySitterList).isNotNull();
        assertThat(babySitterList.getSize()).isEqualTo(1);
        assertThat(babySitterList.getContent().get(0).equals(babySitter));
    }

    @Test
    @Order(5)
    public void doListByFamilyMustBeOk() {
        // precondition
        given(repository.findAllByFamilyId(FAMILY_ID)).willReturn(List.of(babySitter));
        List<BabySitter> babySitters = service.doListByFamily(onlineUser, FAMILY_ID);
        // verify
        assertThat(babySitters).isNotNull();
        assertThat(babySitters.size()).isEqualTo(1);
        assertThat(babySitters.get(0).equals(babySitter));

    }

    @Test
    @Order(5)
    public void doListByUserMustBeOk() {
        // precondition
        given(repository.findByUserId(USER_ID)).willReturn(List.of(babySitter));
        List<BabySitter> babySitters = service.doListByUser(onlineUser, USER_ID);
        // verify
        assertThat(babySitters).isNotNull();
        assertThat(babySitters.size()).isEqualTo(1);
        assertThat(babySitters.get(0).equals(babySitter));

    }

    @Test
    @Order(7)
    public void cancelAddressTest() {
        babySitter.setCreationAuthorId(this.onlineUser.getId());
        given(repository.findById(babySitter.getId())).willReturn(Optional.of(babySitter));

        // precondition
        given(repository.save(babySitter)).willReturn(babySitter);
        // willDoNothing().given(repository).delete(address);

        // action
        service.doCancel(onlineUser, babySitter.getId());

        // verify
        assertThat(babySitter.getDeletionDate()).isNotNull();
    }

    @Test
    @Order(8)
    public void cancelAddressThrowException() {
        babySitter.setCreationAuthorId(null);
        // precondition
        given(repository.findById(babySitter.getId())).willReturn(Optional.of(babySitter));
        assertThrows(ActionNotAllowedException.class, () -> service.doCancel(onlineUser, babySitter.getId()));
    }
}