package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.castleedev.cabanassyc_backend.DAL.ICabinDAL;
import com.castleedev.cabanassyc_backend.DAL.ICabinImageDAL;
import com.castleedev.cabanassyc_backend.DTO.CabinImageDTO;
import com.castleedev.cabanassyc_backend.Models.Cabin;
import com.castleedev.cabanassyc_backend.Models.CabinImage;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ICabinImageService;


@Service
@Transactional
public class CabinImageService implements ICabinImageService {
    
    private final ICabinImageDAL cabinImageDAL;
    private final ICabinDAL cabinDAL;

    public CabinImageService (ICabinImageDAL cabinImageDAL, ICabinDAL cabinDAL) {
        this.cabinImageDAL = cabinImageDAL;
        this.cabinDAL = cabinDAL;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CabinImageDTO> getAllCabinImages() {
        List<CabinImage> cabinImages = cabinImageDAL.findAllByStateTrue();
        if (cabinImages.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No cabins found"
            );
        }

        return cabinImages.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CabinImageDTO getCabinImageById(Long id) {
        CabinImage cabinImage = cabinImageDAL.findByIdAndStateTrue(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cabin image not found"
            ));

        return convertToDTO(cabinImage);
    }

    @Override
    public CabinImageDTO addCabinImage(CabinImageDTO cabinImageDTO) {
        CabinImage cabinImage = convertToEntity(cabinImageDTO);
        cabinImage.setState(true);
        CabinImage savedCabin = cabinImageDAL.save(cabinImage);
        return convertToDTO(savedCabin);
    }

    @Override
    public List<CabinImageDTO> addAllCabinImage(List<CabinImageDTO> cabinImagesDTO) {
        if (cabinImagesDTO == null || cabinImagesDTO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No images provided");
        }

        Long cabinId = cabinImagesDTO.get(0).getCabinId();
        Cabin cabin = cabinDAL.findByIdAndStateTrue(cabinId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cabin not found"));

        List<CabinImage> images = cabinImagesDTO.stream()
            .map(dto -> new CabinImage(
                null, 
                cabin,
                dto.getUrl(),
                dto.isCover(),
                true 
            ))
            .collect(Collectors.toList());

        List<CabinImage> savedImages = cabinImageDAL.saveAll(images);
        return savedImages.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }


    @Override
    public void deleteCabinImage(Long id) {
        if (cabinImageDAL.findByIdAndStateTrue(id).isEmpty()) {

            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cabin image not found"
            );
        }
        int rowsAffected = cabinImageDAL.softDeleteById(id);
        if (rowsAffected <= 0) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Failed deleting cabin image"
            );
        }
    }

    private CabinImageDTO convertToDTO(CabinImage cabinImage) {
        if (cabinImage == null) return null;
        
        return new CabinImageDTO(
            cabinImage.getId(),
            cabinImage.getCabin().getId(),
            cabinImage.getUrl(),
            cabinImage.isCover(),
            cabinImage.isState()
        );
    }

    private CabinImage convertToEntity(CabinImageDTO cabinImageDTO) {
        if (cabinImageDTO == null) return null;

        Cabin cabin = cabinDAL.findByIdAndStateTrue(cabinImageDTO.getCabinId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cabin not found"
            ));

        return new CabinImage(
            cabinImageDTO.getId(),
            cabin,
            cabinImageDTO.getUrl(),
            cabinImageDTO.isCover(),
            cabinImageDTO.isState()
        );
    }
}
