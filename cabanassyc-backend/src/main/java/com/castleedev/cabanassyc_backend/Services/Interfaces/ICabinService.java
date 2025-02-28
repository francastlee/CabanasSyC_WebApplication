package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.Models.Cabin;

public interface ICabinService {

    public List<Cabin> getAllCabins();
    public Cabin getCabinById(Long id);
    public Cabin addCabin(Cabin cabin);
    public Cabin updateCabin(Cabin cabin);
    public void deleteCabin(Long id);
    
} 