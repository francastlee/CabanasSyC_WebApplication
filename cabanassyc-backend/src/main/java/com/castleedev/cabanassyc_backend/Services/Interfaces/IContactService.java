package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.DTO.ContactDTO;

public interface IContactService {
    
    public List<ContactDTO> getAllContacts();
    public ContactDTO getContactById(Long id);
    public ContactDTO addContact(ContactDTO contact);
    public ContactDTO updateContact(ContactDTO contact);
    public void deleteContact(Long id);
    
}
