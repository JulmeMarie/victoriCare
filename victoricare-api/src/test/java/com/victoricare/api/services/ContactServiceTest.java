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
import com.victoricare.api.services.impl.ContactService;
import com.victoricare.api.validators.impl.PageValidator;
import com.victoricare.api.repositories.IContactRepository;
import com.victoricare.api.repositories.IUserRepository;
import com.victoricare.api.security.JWTToken;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import com.victoricare.api.dtos.inputs.ContactInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.entities.Contact;
import com.victoricare.api.entities.User;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.mailers.IContactMailer;

@DisplayName("ContactService Tests")
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContactServiceTest {
    private static final String NAME = "Jean Pierre";
    private static final String TEST_SUBJECT = "New York";
    private static final String TEST_MESSAGE = "88 Rue Vivaldi";
    private static final Integer USER_ID = 1;
    private static final Integer ID = 1;
    private static final String TEST_EMAIL = "test@example.com";

    @Mock
    private IContactRepository repository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserService userService;

    @Mock
    private IContactMailer contactMailer;

    @Mock
    private JWTToken jwtToken;

    @InjectMocks
    private ContactService service;

    private Contact contact;
    private ContactInputDTO contactInputDTO;
    private User user = User.builder().id(USER_ID).build();

    @BeforeEach
    public void setup() {

        contact = Contact.builder()
                .id(ID)
                .name(NAME)
                .subject(TEST_SUBJECT)
                .text(TEST_MESSAGE)
                .email(TEST_EMAIL)
                .build();

        contactInputDTO = new ContactInputDTO();
        contactInputDTO.setName(NAME);
        contactInputDTO.setEmail(TEST_EMAIL);
        contactInputDTO.setText(TEST_MESSAGE);
        contactInputDTO.setSubject(TEST_SUBJECT);

    }

    @Test
    @Order(1)
    public void shouldCreateContactSuccessfully() {
        // precondition
        given(repository.save(Mockito.any(Contact.class))).willReturn(contact);
        willDoNothing().given(this.contactMailer).mailForContactUs(Mockito.any(Contact.class));
        Contact savedContact = service.doCreate(this.jwtToken, contactInputDTO);

        assertThat(savedContact.getCreationDate()).isNotNull(); // Verify creation date
    }

    @Test
    @Order(2)
    public void shouldDoResponseSuccessfully() {
        String response = "TEST_RESPONSE";
        // precondition
        given(this.repository.findById(ID)).willReturn(Optional.of(contact));
        given(repository.save(Mockito.any(Contact.class))).willReturn(contact);
        willDoNothing().given(this.contactMailer).mailForResponse(Mockito.any(Contact.class));

        Contact savedContact = service.doResponse(this.user, response, ID);

        assertThat(savedContact.getResponseDate()).isNotNull(); // Verify creation date
    }

    @Test
    @Order(3)
    public void getContactByIdTest() {
        contact.setId(ID);
        // precondition
        given(repository.findById(ID)).willReturn(Optional.of(contact));

        // action
        Contact existingContact = service.doGet(user, ID);

        // verify
        assertThat(existingContact).isNotNull();

    }

    @Test
    @Order(2)
    public void getDeletedContactByIdThrowException() {
        // precondition
        given(repository.findById(contact.getId())).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.doGet(user, contact.getId()));
    }

    @Test
    @Order(3)
    public void getPageTest() {
        PageInputDTO page = new PageInputDTO();
        Pageable pageable = PageValidator.getPageable(Contact.class, page);
        Page<Contact> contactPage = new PageImpl<Contact>(List.of(contact));

        // precondition
        given(repository.findAll(pageable)).willReturn(contactPage);

        // action
        Page<Contact> contactList = service.doPage(pageable);

        // verify
        assertThat(contactList).isNotNull();
        assertThat(contactList.getSize()).isEqualTo(1);
        assertThat(contactList.getContent().get(0).equals(contact));

    }

    @Test
    @Order(4)
    public void getListByUserTest() {
        // precondition
        given(repository.findAllByUser(USER_ID)).willReturn(List.of(contact));

        // action
        List<Contact> contactList = service.doListByUser(user, USER_ID);

        // verify
        assertThat(contactList).isNotNull();
        assertThat(contactList.size()).isEqualTo(1);
        assertThat(contactList.get(0).equals(contact));

    }

    @Test
    @Order(9)
    public void deleteContactTest() {

        given(repository.findById(contact.getId())).willReturn(Optional.of(contact));
        // precondition
        willDoNothing().given(repository).delete(contact);

        // action
        service.doDelete(user, contact.getId());

        // verify
        verify(repository, times(1)).delete(contact);
    }
}