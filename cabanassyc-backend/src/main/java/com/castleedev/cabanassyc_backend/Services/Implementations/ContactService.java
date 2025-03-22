package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.IContactDAL;
import com.castleedev.cabanassyc_backend.DTO.ContactDTO;
import com.castleedev.cabanassyc_backend.Models.Contact;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IContactService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ContactService implements IContactService {

    @Autowired
    private IContactDAL contactDAL;

    ContactDTO convertir (Contact contact) {
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

    Contact convertir (ContactDTO contactDTO) {
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

    @Override
    public List<ContactDTO> getAllContacts() {
        try {
            List<Contact> contacts = contactDAL.findAllByStateTrue();
            List<ContactDTO> contactsDTO = new ArrayList<ContactDTO>();
            for (Contact contact : contacts) {
                contactsDTO.add(convertir(contact));
            }
            return contactsDTO; 
        } catch (Exception e) {
            throw new RuntimeException("Error getting all contacts", e);
        }
    }

    @Override
    public ContactDTO getContactById(Long id) {
        try {
            Contact contact = contactDAL.findByIdAndStateTrue(id);
            if (contact != null) {
                return convertir(contact);
            } else {
                throw new EntityNotFoundException("Contact not found or already deleted");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error getting a contact", e);
        }
    }

    @Override
    public ContactDTO addContact(ContactDTO contactDTO) {
        try {
            Contact contact = convertir(contactDTO);
            return convertir(contactDAL.save(contact));
        } catch (Exception e) {
            throw new RuntimeException("Error adding a contact", e);
        }
    }

    @Override
    public ContactDTO updateContact(ContactDTO contactDTO) {
        try {
            Contact contact = new Contact(
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
            return convertir(contactDAL.save(contact));
        } catch (Exception e) {
            throw new RuntimeException("Error updating a contact", e);
        }
    }

    @Override
    public void deleteContact(Long id) {
        Contact contact = contactDAL.findByIdAndStateTrue(id);
        if (contact != null) {
            contactDAL.softDeleteById(id);
        } else {
            throw new EntityNotFoundException("Contact not found or already deleted");
        }
    }
    
}