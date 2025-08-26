package com.castleedev.cabanassyc_backend.DAL;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.castleedev.cabanassyc_backend.Models.TourImage;

public interface ITourImageDAL extends JpaRepository<TourImage, Long> {
    List<TourImage> findAllByTourIdAndStateTrue(Long tourId);
}
