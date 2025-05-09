package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;
import com.castleedev.cabanassyc_backend.DTO.CabinImageDTO;

public interface ICabinImageService {
    
    public List<CabinImageDTO> getAllCabinImages();
    public CabinImageDTO getCabinImageById(Long id);
    public CabinImageDTO addCabinImage(CabinImageDTO cabinImageDTO);
    public List<CabinImageDTO> addAllCabinImage(List<CabinImageDTO> cabinImages);
    public void deleteCabinImage(Long id);
}
