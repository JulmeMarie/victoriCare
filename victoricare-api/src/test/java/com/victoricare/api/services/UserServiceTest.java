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
import com.victoricare.api.services.impl.UserService;
import com.victoricare.api.services.impl.CodeService;
import com.victoricare.api.validators.impl.PageValidator;
import com.victoricare.api.repositories.IUserRepository;
import com.victoricare.api.security.JWTToken;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import com.victoricare.api.dtos.inputs.UserInputDTO;
import com.victoricare.api.dtos.inputs.AddressInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.entities.BabySitter;
import com.victoricare.api.entities.ChildCare;
import com.victoricare.api.entities.Code;
import com.victoricare.api.entities.Family;
import com.victoricare.api.entities.User;
import com.victoricare.api.entities.Address;
import com.victoricare.api.exceptions.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {
    private static final String NAME = "NAME_TEST";
    private static final String EMAIL = "EMAIL_TEST";
    private static final Integer USER_ID = 1;
    private static final Integer ID = 1;

    @Mock
    private IUserRepository repository;

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
    private UserService service;

    private User user;
    private UserInputDTO userInputDTO;
    private User onlineUser = User.builder().id(USER_ID).build();

    @BeforeEach
    public void setup() {
        this.initUser();
        this.initInput();
    }

    private void initUser() {
        this.user = User.builder()
                .id(ID)
                .firstname(NAME)
                .lastname("TEST")
                .build();
    }

    private void initInput() {
        userInputDTO = new UserInputDTO();
        userInputDTO.setFirstname(NAME);
        userInputDTO.setLastname("TEST");
        userInputDTO.setAddress(new AddressInputDTO());
    }

    @Test
    @Order(1)
    public void doCreateUserMustBeOKTest() {
        Code code = new Code();
        // precondition
        given(this.addressService.doCreate(USER_ID, userInputDTO.getAddress())).willReturn(new Address());
        given(this.codeService.doCreate(CodeService.ECodeLength.TEN)).willReturn(code);
        given(repository.save(Mockito.any(User.class))).willReturn(user);

        // action
        User savedUser = service.doCreate(this.onlineUser, userInputDTO);

        // verify the output
        assertThat(savedUser.getCreationDate()).isNotNull();
    }

    @Test
    @Order(2)
    public void doUpdateUserMustBeOKTest() {
        Integer addressId = 1;
        userInputDTO.getAddress().setId(addressId);
        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(this.user));

        given(this.addressService.doUpdate(Mockito.any(User.class), Mockito.any(AddressInputDTO.class),
                Mockito.any(Integer.class))).willReturn(null);

        given(repository.save(Mockito.any(User.class))).willReturn(this.user);

        // action
        User savedUser = service.doUpdate(this.onlineUser, userInputDTO, ID);

        // verify the output
        assertThat(savedUser.getUpdateDate()).isNotNull();
    }

    @Test
    @Order(4)
    public void doDeleteUserMustBeOKTest() {
        // precondition
        given(repository.findById(user.getId())).willReturn(Optional.of(user));

        willDoNothing().given(repository).delete(user);
        service.doDelete(onlineUser, user.getId());
    }

    @Test
    @Order(5)
    public void getPageTest() {
        PageInputDTO page = new PageInputDTO();
        page.setSort(new String[] { "pseudo,ASC" });
        Pageable pageable = PageValidator.getPageable(User.class, page);
        Page<User> userPage = new PageImpl<User>(List.of(user));

        // precondition
        given(repository.findAll(pageable)).willReturn(userPage);

        // action
        Page<User> userList = service.doPage(pageable);

        // verify
        System.out.println(userList);
        assertThat(userList).isNotNull();
        assertThat(userList.getSize()).isEqualTo(1);
        assertThat(userList.getContent().get(0).equals(user));
    }

    @Test
    @Order(5)
    public void doListAdminsBeOk() {
        // precondition
        given(repository.findAllAdmins()).willReturn(List.of(user));
        List<User> userren = service.getAllAdmins();
        // verify
        assertThat(userren).isNotNull();
        assertThat(userren.size()).isEqualTo(1);
        assertThat(userren.get(0).equals(user));

    }

    @Test
    @Order(4)
    public void doGetUserMustBeOKTest() {
        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(this.user));
        User existingUser = service.doGet(this.onlineUser, ID);
        assertThat(existingUser.equals(user));
    }

    @Test
    @Order(4)
    public void doGetByEmailUserMustBeOKTest() {
        // precondition
        given(repository.findByEmail(EMAIL)).willReturn(Optional.of(this.user));
        User existingUser = service.doGet(EMAIL);
        assertThat(existingUser.equals(user));
    }

    @Test
    @Order(4)
    public void doGetNonExistingUserMustThrowExceptionTest() {
        // precondition
        given(repository.findById(ID)).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.doGet(this.onlineUser, ID));
    }

    @Test
    @Order(4)
    public void doListUserMustBeOKTest() {
        // precondition
        given(repository.findByEmailOrPseudo(EMAIL, NAME)).willReturn(List.of(this.user));
        List<User> list = service.doListByEmailOrPseudo(EMAIL, NAME);

        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).equals(user));
    }

    @Test
    @Order(4)
    public void isParentMustBeTrueWithParentTwoTest() {
        // Preconditions
        Family family = Family.builder().parentTwo(onlineUser).build();
        assertTrue(UserService.isParent(USER_ID, family));
    }

    @Test
    @Order(4)
    public void isParentMustBeTrueWithParentOneTest() {
        // Preconditions
        Family family = Family.builder().parentOne(onlineUser).build();
        assertTrue(UserService.isParent(USER_ID, family));
    }

    @Test
    @Order(4)
    public void isParentMustBeTrueWithCreatorTest() {
        // Preconditions
        Family family = Family.builder().creationAuthorId(ID).build();
        assertTrue(UserService.isParent(USER_ID, family));
    }

    @Test
    @Order(4)
    public void isParentMustBeFalseTest() {
        // Preconditions
        Family family = new Family();
        assertFalse(UserService.isParent(USER_ID, family));
    }

    @Test
    @Order(4)
    public void isBabySitterMustBeTrueTest() {
        BabySitter babySitter = BabySitter.builder().userId(USER_ID).build();
        // Preconditions
        Family family = Family.builder().babySitter(babySitter).build();
        assertTrue(UserService.isBabySitter(USER_ID, family));
    }

    @Test
    @Order(4)
    public void isBabySitterMustBeFalseTest() {
        // Preconditions
        Family family = new Family();
        assertFalse(UserService.isBabySitter(USER_ID, family));
    }

    @Test
    @Order(4)
    public void isBabySitterChildCareMustBeTrueTest() {
        BabySitter babySitter = BabySitter.builder().userId(USER_ID).build();
        // Preconditions
        ChildCare childCare = ChildCare.builder().babySitter(babySitter).build();
        assertTrue(UserService.isBabySitter(USER_ID, childCare));
    }

    @Test
    @Order(4)
    public void isBabySitterChildCareMustBeFalseTest() {
        // Preconditions
        ChildCare childCare = new ChildCare();
        assertFalse(UserService.isBabySitter(USER_ID, childCare));
    }

}