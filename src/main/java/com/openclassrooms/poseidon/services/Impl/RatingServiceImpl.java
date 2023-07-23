package com.openclassrooms.poseidon.services.Impl;

import com.openclassrooms.poseidon.domain.Rating;
import com.openclassrooms.poseidon.repositories.RatingRepository;
import com.openclassrooms.poseidon.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;


    @Override
    public void saveRating(Rating rating) {
        ratingRepository.save(rating);
    }

    @Override
    public List<Rating> listAll() {
        return ratingRepository.findAll();
    }

//    public List<Object[]> getAllRatings() {
//        return ratingRepository.findAllRatings();
//    }

    @Override
    public Rating get(Long id) {
        return ratingRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        ratingRepository.deleteById(id);
    }
}
