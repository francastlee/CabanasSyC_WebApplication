package com.castleedev.cabanassyc_backend.Controllers;

import java.util.List;

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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    
    private final IContactService contactService;

    public ContactController(IContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ContactDTO>>> getAllContacts() {
        List<ContactDTO> contacts = contactService.getAllContacts();
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Contacts found successfully", contacts)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ContactDTO>> getContactById(@PathVariable("id") Long id) {
        ContactDTO contact = contactService.getContactById(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Contact found successfully", contact)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ContactDTO>> addContact(@Valid @RequestBody ContactDTO contactDTO) {
        ContactDTO createdContact = contactService.addContact(contactDTO);
        return ResponseEntity.ok()
            .body(new ApiResponse<>(true, "Contact created successfully", createdContact));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ContactDTO>> updateContact(@Valid @RequestBody ContactDTO contactDTO) {
        ContactDTO updatedContact = contactService.updateContact(contactDTO);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Contact updated successfully", updatedContact)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteContact(@PathVariable("id") Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Contact deleted successfully", null)
        );
    }
}