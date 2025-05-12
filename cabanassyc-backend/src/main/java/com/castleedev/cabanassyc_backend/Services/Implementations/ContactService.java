package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.castleedev.cabanassyc_backend.DAL.IContactDAL;
import com.castleedev.cabanassyc_backend.DTO.ContactDTO;
import com.castleedev.cabanassyc_backend.Models.Contact;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IContactService;

@Service
@Transactional
public class ContactService implements IContactService {

    private final IContactDAL contactDAL;

    public ContactService(IContactDAL contactDAL) {
        this.contactDAL = contactDAL;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactDTO> getAllContacts() {
        List<Contact> contacts = contactDAL.findAllByStateTrue();
        if (contacts.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "No contacts found"
            );
        }

        return contacts.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ContactDTO getContactById(Long id) {
        Contact contact = contactDAL.findByIdAndStateTrue(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Contact not found"
            ));

        return convertToDTO(contact);
    }

    @Override
    public ContactDTO addContact(ContactDTO contactDTO) {
        Contact contact = convertToEntity(contactDTO);
        contact.setState(true);
        contact.setRead(false);
        Contact savedContact = contactDAL.save(contact);

        return convertToDTO(savedContact);
    }

    @Override
    public ContactDTO updateContact(ContactDTO contactDTO) {
        Contact existingContact = contactDAL.findByIdAndStateTrue(contactDTO.getId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Contact not found"
            ));
        
        existingContact.setFirstName(contactDTO.getFirstName());
        existingContact.setLastName(contactDTO.getLastName());
        existingContact.setEmail(contactDTO.getEmail());
        existingContact.setPhone(contactDTO.getPhone());
        existingContact.setMessage(contactDTO.getMessage());
        existingContact.setRead(contactDTO.isRead());
        existingContact.setDate(contactDTO.getDate());
        existingContact.setState(contactDTO.isState());
        existingContact = contactDAL.save(existingContact);
        
        return convertToDTO(existingContact);
    }

    @Override
    public void deleteContact(Long id) {
        if (contactDAL.findByIdAndStateTrue(id).isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Contact not found"
            );
        }
        int rowAffected = contactDAL.softDeleteById(id);
        if (rowAffected <= 0) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Failed to delete contact"
            );
        }
    }

    private ContactDTO convertToDTO(Contact contact) {
        if (contact == null) return null;
        
        return new ContactDTO(
            contact.getId(),
            contact.getFirstName(),
            contact.getLastName(),
            contact.getEmail(),
            contact.getPhone(),
            contact.getMessage(),
            contact.getDate(),
            contact.isRead(),
            contact.isState()
        );
    }

    private Contact convertToEntity(ContactDTO contactDTO) {
        if (contactDTO == null) return null;
        
        return new Contact(
            contactDTO.getId(),
            contactDTO.getFirstName(),
            contactDTO.getLastName(),
            contactDTO.getEmail(),
            contactDTO.getPhone(),
            contactDTO.getMessage(),
            contactDTO.getDate(),
            contactDTO.isRead(),
            contactDTO.isState()
        );
    }
}