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
import com.victoricare.api.services.impl.CodeService;
import com.victoricare.api.validators.impl.PageValidator;
import com.victoricare.api.repositories.IFamilyRepository;
import com.victoricare.api.repositories.IUserRepository;
import com.victoricare.api.security.JWTToken;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import com.victoricare.api.dtos.inputs.FamilyInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.entities.Family;
import com.victoricare.api.entities.User;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FamilyServiceTest {
    private static final String NAME = "TASKNAME_TEST";
    private static final Integer USER_ID = 1;
    private static final Integer FAMILY_ID = 1;
    private static final Integer ID = 1;

    @Mock
    private IFamilyRepository repository;

    @Mock
    private FamilyService familyService;

    @Mock
    private CodeService codeService;

    @Mock
    private JWTToken jwtToken;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private FamilyService service;

    private Family family;

    private FamilyInputDTO familyInputDTO;
    private User onlineUser = User.builder().id(USER_ID).build();

    @BeforeEach
    public void setup() {
        this.family = Family.builder().id(FAMILY_ID).build();
        this.initFamily();
        this.initInput();

    }

    private void initFamily() {
        this.family = Family.builder()
                .id(ID)
                .build();
    }

    private void initInput() {
        familyInputDTO = new FamilyInputDTO();
    }

    @Test
    @Order(1)
    public void doCreateFamilyMustBeOKTest() {

        given(repository.save(Mockito.any(Family.class))).willReturn(family);

        // action
        Family savedFamily = service.doCreate(this.onlineUser, familyInputDTO);

        // verify the output
        assertThat(savedFamily.getCreationDate()).isNotNull();
    }

    @Test
    @Order(2)
    public void doUpdateFamilyMustBeOKTest() {

        this.family.setCreationAuthorId(USER_ID);

        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(this.family));
        given(repository.save(Mockito.any(Family.class))).willReturn(this.family);

        // action
        Family savedFamily = service.doUpdate(this.onlineUser, familyInputDTO, ID);

        // verify the output
        assertThat(savedFamily.getUpdateDate()).isNotNull();
    }

    @Test
    @Order(3)
    public void doUpdateFamilyNotAuthorMustThrowExceptionTest() {
        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(this.family));
        assertThrows(ActionNotAllowedException.class, () -> service.doUpdate(this.onlineUser, familyInputDTO, ID));
    }

    @Test
    @Order(3)
    public void doUpdateDeletedFamilyMustThrowExceptionTest() {
        this.family.setCreationAuthorId(USER_ID);
        this.family.setDeletionDate(new Date());
        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(this.family));
        assertThrows(ActionNotAllowedException.class, () -> service.doUpdate(this.onlineUser, familyInputDTO, ID));
    }

    @Test
    @Order(4)
    public void doGetNonExistingFamilyMustThrowExceptionTest() {
        // precondition
        given(repository.findById(ID)).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.doGet(this.onlineUser, ID));
    }

    @Test
    @Order(4)
    public void doDeleteFamilyCareTaskMustBeOKTest() {
        // precondition
        given(repository.findById(family.getId())).willReturn(Optional.of(family));

        willDoNothing().given(repository).delete(family);
        service.doDelete(onlineUser, family.getId());
    }

    @Test
    @Order(4)
    public void getPageTest() {
        PageInputDTO page = new PageInputDTO();
        Pageable pageable = PageValidator.getPageable(Family.class, page);
        Page<Family> familyPage = new PageImpl<Family>(List.of(family));

        // precondition
        given(repository.findAll(pageable)).willReturn(familyPage);

        // action
        Page<Family> familyList = service.doPage(pageable);

        // verify
        System.out.println(familyList);
        assertThat(familyList).isNotNull();
        assertThat(familyList.getSize()).isEqualTo(1);
        assertThat(familyList.getContent().get(0).equals(family));
    }

    @Test
    @Order(5)
    public void doListByParentMustBeOk() {
        // precondition
        given(repository.findAllByParent(USER_ID)).willReturn(List.of(family));
        List<Family> familyren = service.doListByParent(onlineUser, USER_ID);
        // verify
        assertThat(familyren).isNotNull();
        assertThat(familyren.size()).isEqualTo(1);
        assertThat(familyren.get(0).equals(family));

    }
}