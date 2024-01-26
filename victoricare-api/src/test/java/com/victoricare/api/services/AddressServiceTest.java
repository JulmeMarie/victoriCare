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
import com.victoricare.api.services.impl.AddressService;
import com.victoricare.api.validators.impl.PageValidator;
import com.victoricare.api.repositories.IAddressRepository;
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
import com.victoricare.api.dtos.inputs.AddressInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.entities.Address;
import com.victoricare.api.entities.User;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;

@DisplayName("AddressService Tests")
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddressServiceTest {
    private static final Integer USER_ID = 1;
    private static final Integer ID = 1;

    @Mock
    private IAddressRepository repository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserService userService;

    @InjectMocks
    private AddressService service;

    private Address address;
    private AddressInputDTO addressInputDTO;
    private User user = User.builder().id(USER_ID).build();

    @BeforeEach
    public void setup() {
        address = Address.builder().id(25).creationAuthorId(USER_ID).build();
        addressInputDTO = new AddressInputDTO();
    }

    @Test
    @Order(1)
    @DisplayName("For a given correct addressInputDTO, " +
            "we should create and return successfully a new address with a valid creation date and a valid creation author ID")
    public void shouldCreateAddressSuccessfully() {
        // precondition
        given(repository.save(Mockito.any(Address.class))).willReturn(address);

        // action
        Address savedAddress = service.doCreate(user.getId(), addressInputDTO);

        // verify the output
        assertThat(savedAddress.getCreationDate()).isNotNull(); // Verify creation date
        assertThat(savedAddress.getCreationAuthorId()).isEqualTo(address.getCreationAuthorId()); // Creation author must
                                                                                                 // match the user
    }

    @Test
    @Order(2)
    public void getAddressByIdTest() {
        address.setId(ID);
        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(address));

        // action
        Address existingAddress = service.doGet(user.getId(), ID);

        // verify
        assertThat(existingAddress).isNotNull();

    }

    @Test
    @Order(2)
    public void dotCanceledAddressByIdThrowException() {
        address.setDeletionDate(new Date());
        // precondition
        given(repository.findById(address.getId())).willReturn(Optional.of(address));
        assertThrows(ResourceNotFoundException.class, () -> service.doCancel(user, address.getId()));
    }

    @Test
    @Order(2)
    public void getDeletedAddressByIdThrowException() {
        // precondition
        given(repository.findById(address.getId())).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.doGet(user.getId(), address.getId()));
    }

    @Test
    @Order(3)
    public void getPageTest() {
        PageInputDTO page = new PageInputDTO();
        Pageable pageable = PageValidator.getPageable(Address.class, page);
        Page<Address> addressPage = new PageImpl<Address>(List.of(address));

        // precondition
        given(repository.findAll(pageable)).willReturn(addressPage);

        // action
        Page<Address> addressList = service.doPage(pageable);

        // verify
        assertThat(addressList).isNotNull();
        assertThat(addressList.getSize()).isEqualTo(1);
        assertThat(addressList.getContent().get(0).equals(address));

    }

    @Test
    @Order(4)
    public void getListByUserTest() {
        // precondition
        given(repository.findAllByUserId(USER_ID)).willReturn(List.of(address));

        // action
        List<Address> addressList = service.doListByUser(user, USER_ID);

        // verify
        assertThat(addressList).isNotNull();
        assertThat(addressList.size()).isEqualTo(1);
        assertThat(addressList.get(0).equals(address));

    }

    @Test
    @Order(5)
    public void updateAddress() {

        // precondition
        given(repository.findById(address.getId())).willReturn(Optional.of(address));
        given(repository.save(address)).willReturn(address);
        addressInputDTO.setCountry("FRANCE");

        // action
        Address updatedAddress = service.doUpdate(user, addressInputDTO, address.getId());

        // verify
        System.out.println(updatedAddress);
        assertThat(updatedAddress.getCountry()).isEqualTo("FRANCE");
        assertThat(updatedAddress.getUpdateDate()).isNotNull();
    }

    @Test
    @Order(6)
    public void updateAddressUnerNotAuthorThrowException() {
        Integer otherUserId = 2;
        address.setCreationAuthorId(otherUserId);
        // precondition
        given(repository.findById(address.getId())).willReturn(Optional.of(address));
        assertThrows(ActionNotAllowedException.class, () -> service.doUpdate(user, addressInputDTO, address.getId()));
    }

    @Test
    @Order(6)
    public void updateDeletedAddressThrowException() {
        address.setDeletionDate(new Date());
        // precondition
        given(repository.findById(address.getId())).willReturn(Optional.of(address));
        assertThrows(ResourceNotFoundException.class, () -> service.doUpdate(user, addressInputDTO, address.getId()));
    }

    @Test
    @Order(7)
    public void cancelAddressTest() {

        given(repository.findById(address.getId())).willReturn(Optional.of(address));

        // precondition
        given(repository.save(address)).willReturn(address);
        // willDoNothing().given(repository).delete(address);

        // action
        service.doCancel(user, address.getId());

        // verify
        assertThat(address.getDeletionDate()).isNotNull();
    }

    @Test
    @Order(8)
    public void cancelAddressThrowException() {
        Integer otherUserId = 2;
        address.setCreationAuthorId(otherUserId);
        // precondition
        given(repository.findById(address.getId())).willReturn(Optional.of(address));
        assertThrows(ActionNotAllowedException.class, () -> service.doCancel(user, address.getId()));
    }

    @Test
    @Order(9)
    public void deleteAddressTest() {

        given(repository.findById(address.getId())).willReturn(Optional.of(address));

        // precondition
        willDoNothing().given(repository).delete(address);

        // action
        service.doDelete(this.user, address.getId());

        // verify
        verify(repository, times(1)).delete(address);
    }
}