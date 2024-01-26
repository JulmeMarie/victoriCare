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
import com.victoricare.api.validators.impl.PageValidator;
import com.victoricare.api.repositories.IChildCareRepository;
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
import com.victoricare.api.dtos.inputs.ChildCareInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.entities.BabySitter;
import com.victoricare.api.entities.ChildCare;
import com.victoricare.api.entities.Family;
import com.victoricare.api.entities.User;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ChildCareServiceTest {
    private static final Integer USER_ID = 1;
    private static final Integer FAMILY_ID = 1;
    private static final Integer CHILD_ID = 1;
    private static final Long CHILDCARE_ID = 1L;
    private static final Long ID = 1L;

    @Mock
    private IChildCareRepository repository;

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
    private ChildCareService service;

    private Family family;

    private ChildCare childCare;
    private ChildCareInputDTO childCareInputDTO;
    private User onlineUser = User.builder().id(USER_ID).build();

    @BeforeEach
    public void setup() {
        this.family = Family.builder().id(FAMILY_ID).build();
        this.childCare = ChildCare.builder().id(CHILDCARE_ID).creationFamilyId(FAMILY_ID).build();
        this.initInput();
    }

    private void initInput() {
        childCareInputDTO = new ChildCareInputDTO();
        childCareInputDTO.setCreationFamilyId(FAMILY_ID);

        childCareInputDTO.setStartDate(new Date());
        childCareInputDTO.setEndDate(new Date(new Date().getTime() + 7 * 60 * 60 * 1000));
    }

    @Test
    @Order(1)
    public void doCreateChildCareMustBeOKTest() {
        this.family.setCreationAuthorId(onlineUser.getId());
        this.childCare.setBabySitter(BabySitter.builder().userId(USER_ID).build());
        // precondition
        given(familyService.doGet(onlineUser, childCare.getCreationFamilyId())).willReturn(this.family);
        // given(service.isAResponsible(user.getId(), family,
        // childCare)).willReturn(true);
        given(repository.save(Mockito.any(ChildCare.class))).willReturn(childCare);

        // action
        ChildCare savedChildCare = service.doCreate(this.onlineUser, childCareInputDTO);

        // verify the output
        assertThat(savedChildCare.getCreationDate()).isNotNull();
    }

    @Test
    @Order(1)
    public void doCreateChildCareNotResponsibleMustMustThrowExceptionTest() {
        // precondition
        this.childCare.setBabySitter(null);
        given(familyService.doGet(onlineUser, childCare.getCreationFamilyId())).willReturn(this.family);
        assertThrows(ActionNotAllowedException.class, () -> service.doCreate(this.onlineUser, childCareInputDTO));
    }

    @Test
    @Order(2)
    public void doUpdateChildCareMustBeOKTest() {
        // this.childCare.setBabySitter(BabySitter.builder().userId(USER_ID).build());
        this.family.setCreationAuthorId(onlineUser.getId());
        // precondition
        given(familyService.doGet(onlineUser, FAMILY_ID)).willReturn(this.family);

        given(repository.findById(ID)).willReturn(Optional.of(this.childCare));

        given(repository.save(Mockito.any(ChildCare.class))).willReturn(this.childCare);

        // action
        ChildCare savedChildCare = service.doUpdate(this.onlineUser, childCareInputDTO, ID);

        // verify the output
        assertThat(savedChildCare.getUpdateDate()).isNotNull();
    }

    @Test
    @Order(3)
    public void doUpdateChildCareNotResponsibleMustMustThrowExceptionTest() {
        // precondition

        this.childCare.setBabySitter(null);
        // given(repository.findById(ID)).willReturn(Optional.of(this.childCare));
        given(familyService.doGet(onlineUser, childCare.getCreationFamilyId())).willReturn(this.family);
        assertThrows(ActionNotAllowedException.class,
                () -> service.doUpdate(this.onlineUser, childCareInputDTO, ID));
    }

    @Test
    @Order(3)
    public void doUpdateChildCareNotSameFamilyMustMustThrowExceptionTest() {
        Integer otherFamilyID = 2;
        this.family.setCreationAuthorId(onlineUser.getId());
        // precondition
        given(familyService.doGet(onlineUser, FAMILY_ID)).willReturn(this.family);

        childCare.setCreationFamilyId(otherFamilyID);

        given(repository.findById(ID)).willReturn(Optional.of(this.childCare));

        assertThrows(ActionNotAllowedException.class,
                () -> service.doUpdate(this.onlineUser, childCareInputDTO, ID));
    }

    @Test
    @Order(3)
    public void doCancelChildCareMustBeOKTest() {
        this.family.setCreationAuthorId(FAMILY_ID);
        given(repository.findById(childCare.getId())).willReturn(Optional.of(childCare));
        given(familyService.doGet(onlineUser, FAMILY_ID)).willReturn(this.family);
        given(repository.save(childCare)).willReturn(childCare);

        service.doCancel(onlineUser, childCare.getId());

        assertThat(childCare.getDeletionDate()).isNotNull();
    }

    @Test
    @Order(3)
    public void doCancelChildCareNotResponsibleTest() {
        given(repository.findById(childCare.getId())).willReturn(Optional.of(childCare));
        given(familyService.doGet(onlineUser, FAMILY_ID)).willReturn(this.family);
        assertThrows(ActionNotAllowedException.class, () -> service.doCancel(onlineUser, childCare.getId()));
    }

    @Test
    @Order(4)
    public void doDeleteChildCareMustBeOKTest() {
        // precondition
        given(repository.findById(childCare.getId())).willReturn(Optional.of(childCare));
        willDoNothing().given(repository).delete(childCare);
        service.doDelete(onlineUser, childCare.getId());
    }

    @Test

    @Order(3)
    public void getPageTest() {
        PageInputDTO page = new PageInputDTO();
        Pageable pageable = PageValidator.getPageable(ChildCare.class, page);
        Page<ChildCare> childCarePage = new PageImpl<ChildCare>(List.of(childCare));

        // precondition
        given(repository.findAll(pageable)).willReturn(childCarePage);

        // action
        Page<ChildCare> childCareList = service.doPage(pageable);

        // verify
        System.out.println(childCareList);
        assertThat(childCareList).isNotNull();
        assertThat(childCareList.getSize()).isEqualTo(1);
        assertThat(childCareList.getContent().get(0).equals(childCare));
    }

    @Test
    @Order(2)
    public void doGetMustBeOKTest() {
        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(childCare));

        // action
        ChildCare existingChildCare = service.doGet(onlineUser, ID);

        // verify
        assertThat(existingChildCare).isNotNull();
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
        given(repository.findAllByFamily(FAMILY_ID)).willReturn(List.of(this.childCare));
        List<ChildCare> childCares = service.doListByFamily(onlineUser, FAMILY_ID);

        // verify
        assertThat(childCares.size()).isEqualTo(1);
        assertThat(childCares.get(0).equals(childCare));
    }
}