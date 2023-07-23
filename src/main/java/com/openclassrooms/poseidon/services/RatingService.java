package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.Rating;

import java.util.List;


public interface RatingService {

    void saveRating(Rating rating);

    List<Rating> listAll();

//    List<Object[]> getAllRatings();
//

    //public Rating getRatingById(Long id);

    void delete(Long id);

    Rating get(Long id);
}
