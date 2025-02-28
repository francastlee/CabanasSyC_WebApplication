package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.IContactDAL;
import com.castleedev.cabanassyc_backend.Models.Contact;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IContactService;

@Service
public class ContactService implements IContactService {

    @Autowired
    private IContactDAL contactDAL;

    @Override
    public List<Contact> getAllContacts() {
        try {
            return contactDAL.findAllByStateTrue();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all contacts", e);
        }
    }

    @Override
    public Contact getContactById(Long id) {
        try {
            return contactDAL.findByIdAndStateTrue(id);
        } catch (Exception e) {
            throw new RuntimeException("Error getting a contact", e);
        }
    }

    @Override
    public Contact addContact(Contact contact) {
        try {
            return contactDAL.save(contact);
        } catch (Exception e) {
            throw new RuntimeException("Error adding a contact", e);
        }
    }

    @Override
    public Contact updateContact(Contact contact) {
        try {
            return contactDAL.save(contact);
        } catch (Exception e) {
            throw new RuntimeException("Error updating a contact", e);
        }
    }

    @Override
    public void deleteContact(Long id) {
        try {
            contactDAL.softDeleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting a contact", e);
        }
    }
    
}