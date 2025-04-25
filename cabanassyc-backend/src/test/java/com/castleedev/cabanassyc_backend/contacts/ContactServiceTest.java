package com.castleedev.cabanassyc_backend.contacts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.castleedev.cabanassyc_backend.DAL.IContactDAL;
import com.castleedev.cabanassyc_backend.DTO.ContactDTO;
import com.castleedev.cabanassyc_backend.Models.Contact;
import com.castleedev.cabanassyc_backend.Services.Implementations.ContactService;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private IContactDAL contactDAL;

    @InjectMocks
    private ContactService contactService;

    private Contact testContact;
    private ContactDTO
     testContactDTO;

    @BeforeEach
    void setUp() {
        testContact = new Contact(
            1L, 
            "John", 
            "Doe", 
            "john.doe@example.com", 
            "1234567890", 
            "Test message", 
            Date.valueOf("2023-10-01"), 
            false, 
            true
        );
        
        testContactDTO = new ContactDTO(
            1L, 
            "John", 
            "Doe", 
            "john.doe@example.com", 
            "1234567890", 
            "Test message", 
            Date.valueOf("2023-10-01"), 
            false, 
            true
        );
    }

    @Test
    void getAllContacts_WhenContactsExist_ReturnsContactList() {
        List<Contact> mockContacts = List.of(testContact);
        when(contactDAL.findAllByStateTrue()).thenReturn(mockContacts);

        List<ContactDTO> result = contactService.getAllContacts();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getId()).isEqualTo(1L);
        verify(contactDAL).findAllByStateTrue();
    }

    @Test
    void getAllContacts_WhenNoContacts_ThrowsNotFoundException() {
        when(contactDAL.findAllByStateTrue()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> contactService.getAllContacts())
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("No contacts found");
    }

    @Test
    void getContactById_WhenExists_ReturnsContactDTO() {
        when(contactDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testContact));

        ContactDTO result = contactService.getContactById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void getContactById_WhenNotExists_ThrowsNotFoundException() {
        when(contactDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> contactService.getContactById(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Contact not found");
    }

    @Test
    void addContact_WithValidData_ReturnsSavedContact() {
        when(contactDAL.save(any(Contact.class))).thenReturn(testContact);

        ContactDTO result = contactService.addContact(testContactDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.isRead()).isFalse();
        verify(contactDAL).save(argThat(contact -> 
            !contact.isRead() && contact.isState()
        ));
    }

    @Test
    void updateContact_WithValidData_ReturnsUpdatedContact() {
        ContactDTO updateDTO = new ContactDTO(
            1L, 
            "Updated", 
            "Name", 
            "updated@example.com", 
            "9876543210", 
            "Updated message", 
            Date.valueOf("2023-10-02"), 
            true, 
            true
        );
        
        when(contactDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testContact));
        when(contactDAL.save(any(Contact.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ContactDTO result = contactService.updateContact(updateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("Updated");
        assertThat(result.getLastName()).isEqualTo("Name");
        assertThat(result.isRead()).isTrue();
        verify(contactDAL).save(any(Contact.class));
    }

    @Test
    void updateContact_WithNonExistingId_ThrowsNotFoundException() {
        ContactDTO updateDTO = new ContactDTO(
            99L, 
            "Updated", 
            "Name", 
            "updated@example.com", 
            "9876543210", 
            "Updated message", 
            Date.valueOf("2023-10-02"), 
            true, 
            true
        );
        when(contactDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> contactService.updateContact(updateDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Contact not found");
    }

    @Test
    void deleteContact_WithExistingId_DeletesSuccessfully() {
        when(contactDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testContact));
        when(contactDAL.softDeleteById(1L)).thenReturn(1);

        contactService.deleteContact(1L);

        verify(contactDAL).softDeleteById(1L);
    }

    @Test
    void deleteContact_WithNonExistingId_ThrowsNotFoundException() {
        when(contactDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> contactService.deleteContact(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Contact not found");
    }

    @Test
    void deleteContact_WhenDeleteFails_ThrowsInternalError() {
        when(contactDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testContact));
        when(contactDAL.softDeleteById(1L)).thenReturn(0);

        assertThatThrownBy(() -> contactService.deleteContact(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Failed to delete contact");
    }
}