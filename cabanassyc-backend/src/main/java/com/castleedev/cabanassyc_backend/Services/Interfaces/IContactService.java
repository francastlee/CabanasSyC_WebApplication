package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.Models.Contact;

public interface IContactService {
    
    public List<Contact> getAllContacts();
    public Contact getContactById(Long id);
    public Contact addContact(Contact contact);
    public Contact updateContact(Contact contact);
    public void deleteContact(Long id);
    
}
