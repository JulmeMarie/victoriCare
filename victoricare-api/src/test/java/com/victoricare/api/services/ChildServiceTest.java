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
import com.victoricare.api.services.impl.ChildService;
import com.victoricare.api.services.impl.CodeService;
import com.victoricare.api.validators.impl.PageValidator;
import com.victoricare.api.repositories.IChildRepository;
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

import com.victoricare.api.dtos.inputs.AddressInputDTO;
import com.victoricare.api.dtos.inputs.ChildInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.entities.ChildCareTask;
import com.victoricare.api.entities.Code;
import com.victoricare.api.entities.Address;
import com.victoricare.api.entities.Child;
import com.victoricare.api.entities.Family;
import com.victoricare.api.entities.User;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ChildServiceTest {
    private static final String NAME = "TASKNAME_TEST";
    private static final Integer USER_ID = 1;
    private static final Integer FAMILY_ID = 1;
    private static final Integer ID = 1;

    @Mock
    private IChildRepository repository;

    @Mock
    private FamilyService familyService;

    @Mock
    private CodeService codeService;

    @Mock
    private IAddressService addressService;

    @Mock
    private JWTToken jwtToken;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private ChildService service;

    private Family family;

    private Child child;
    private ChildInputDTO childInputDTO;
    private User onlineUser = User.builder().id(USER_ID).build();

    @BeforeEach
    public void setup() {
        this.family = Family.builder().id(FAMILY_ID).build();
        this.initChild();
        this.initInput();

    }

    private void initChild() {
        this.child = Child.builder()
                .id(ID)
                .firstname(NAME)
                .lastname("TEST")
                .family(this.family)
                .build();
    }

    private void initInput() {
        childInputDTO = new ChildInputDTO();
        childInputDTO.setFirstname(NAME);
        childInputDTO.setFamilyId(FAMILY_ID);
        childInputDTO.setLastname("TEST");

        childInputDTO.setAddressId(1);
    }

    @Test
    @Order(1)
    public void doCreateChildMustBeOKTest() {
        Code code = new Code();
        this.family.setParentTwo(User.builder().id(USER_ID).build()); // Set parent two
        this.family.setId(FAMILY_ID);
        // precondition
        childInputDTO.setAddressId(null); //No address at all
        given(familyService.doGet(onlineUser, this.family.getId())).willReturn(this.family);
        given(this.codeService.doCreate(CodeService.ECodeLength.TEN)).willReturn(code);
        given(repository.save(Mockito.any(Child.class))).willReturn(child);

        // action
        Child savedChild = service.doCreate(this.onlineUser, childInputDTO);

        // verify the output
        assertThat(savedChild.getCreationDate()).isNotNull();
    }

    @Test
    @Order(1)
    public void doCreateChildNotResponsibleMustMustThrowExceptionTest() {
        // precondition
        given(familyService.doGet(onlineUser, this.family.getId())).willReturn(this.family);
        assertThrows(ActionNotAllowedException.class, () -> service.doCreate(this.onlineUser, childInputDTO));
    }

    @Test
    @Order(2)
    public void doUpdateChildMustBeOKTest() {
        // this.family.setParentTwo(User.builder().id(USER_ID).build()); // Set parent
        // two
        this.family.setId(FAMILY_ID);
        given(this.addressService.doGet(USER_ID, childInputDTO.getAddressId())).willReturn(new Address());
        User attachParent = User.builder().id(USER_ID).build();
        Family attachFamily = Family.builder().id(2).parentOne(attachParent).build();
        this.child.setAttachmentFamily(attachFamily);

        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(this.child));
        given(repository.save(Mockito.any(Child.class))).willReturn(this.child);

        // action
        Child savedChild = service.doUpdate(this.onlineUser, childInputDTO, ID);

        // verify the output
        assertThat(savedChild.getUpdateDate()).isNotNull();
    }

    @Test
    @Order(3)
    public void doUpdateChildMustThrowExceptionTest() {
        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(this.child));
        assertThrows(ActionNotAllowedException.class, () -> service.doUpdate(this.onlineUser, childInputDTO, ID));
    }

    @Test
    @Order(4)
    public void doGetNonExistingChildMustThrowExceptionTest() {
        // precondition
        given(repository.findById(ID)).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.doGet(this.onlineUser, ID));
    }

    @Test
    @Order(4)
    public void doDeleteChildCareTaskMustBeOKTest() {
        // precondition
        given(repository.findById(child.getId())).willReturn(Optional.of(child));

        willDoNothing().given(repository).delete(child);
        service.doDelete(onlineUser, child.getId());
    }

    @Test
    @Order(4)
    public void getPageTest() {
        PageInputDTO page = new PageInputDTO();
        Pageable pageable = PageValidator.getPageable(ChildCareTask.class, page);
        Page<Child> childPage = new PageImpl<Child>(List.of(child));

        // precondition
        given(repository.findAll(pageable)).willReturn(childPage);

        // action
        Page<Child> childList = service.doPage(pageable);

        // verify
        System.out.println(childList);
        assertThat(childList).isNotNull();
        assertThat(childList.getSize()).isEqualTo(1);
        assertThat(childList.getContent().get(0).equals(child));
    }

    @Test
    @Order(5)
    public void doListByFamilyMustBeOk() {
        // precondition
        given(repository.findAllByFamily(FAMILY_ID)).willReturn(List.of(child));
        List<Child> children = service.doListByFamily(onlineUser, FAMILY_ID);
        // verify
        assertThat(children).isNotNull();
        assertThat(children.size()).isEqualTo(1);
        assertThat(children.get(0).equals(child));

    }
}