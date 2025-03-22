package com.castleedev.cabanassyc_backend.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.castleedev.cabanassyc_backend.DTO.ContactDTO;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IContactService;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    
    private final IContactService contactService;

    public ContactController(IContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public ResponseEntity<?> getAllContacts() {
        try {
            List<ContactDTO> contacts = contactService.getAllContacts();
            if (contacts.isEmpty()) {
                ApiResponse<ContactDTO> apiResponse = new ApiResponse<>(false, "No contacts found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<List<ContactDTO>> apiResponse = new ApiResponse<>(true, "Contacts found successfully", contacts);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<ContactDTO> apiResponse = new ApiResponse<>(false, "Error during the getAll process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContactById(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<ContactDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            ContactDTO contact = contactService.getContactById(id);
            if (contact == null) {
                ApiResponse<ContactDTO> apiResponse = new ApiResponse<>(false, "Contact not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<ContactDTO> apiResponse = new ApiResponse<>(true, "Contact found successfully", contact);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<ContactDTO> apiResponse = new ApiResponse<>(false, "Error during the getById process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> addContact (@RequestBody ContactDTO contact) {
        try {
            if (contact.getFirstName() == null || contact.getLastName() == null || contact.getEmail() == null || contact.getPhone() == null || contact.getMessage() == null || contact.getDate() == null) {
                ApiResponse<ContactDTO> apiResponse = new ApiResponse<>(false, "First name, last name, email, phone and message are required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            ContactDTO newContact = new ContactDTO();
            newContact.setFirstName(contact.getFirstName());
            newContact.setLastName(contact.getLastName());
            newContact.setEmail(contact.getEmail());
            newContact.setPhone(contact.getPhone());
            newContact.setMessage(contact.getMessage());
            newContact.setDate(contact.getDate());
            newContact.setRead(false);
            newContact.setState(true);
            contactService.addContact(newContact);
            ApiResponse<ContactDTO> apiResponse = new ApiResponse<>(true, "Contact created successfully", newContact);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<ContactDTO> apiResponse = new ApiResponse<>(false, "Error during the add process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContact (@PathVariable("id") Long id,@RequestBody ContactDTO contact) {
        try {
            if (id <= 0) {
                ApiResponse<ContactDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            contact.setId(id);
            ContactDTO updatedContact = contactService.updateContact(contact);
            ApiResponse<ContactDTO> apiResponse = new ApiResponse<>(true, "Contact updated successfully", updatedContact);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<ContactDTO> apiResponse = new ApiResponse<>(false, "Error during the update process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<ContactDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            ContactDTO contact = contactService.getContactById(id);
            if (contact == null) {
                ApiResponse<ContactDTO> apiResponse = new ApiResponse<>(false, "Contact not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            contactService.deleteContact(id);
            ApiResponse<ContactDTO> apiResponse = new ApiResponse<>(true, "Contact deleted successfully", contact);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<ContactDTO> apiResponse = new ApiResponse<>(false, "Error during the delete process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

}