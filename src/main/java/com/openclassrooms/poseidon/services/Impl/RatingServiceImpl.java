package com.openclassrooms.poseidon.services.Impl;
import com.openclassrooms.poseidon.domain.Rating;
import com.openclassrooms.poseidon.repositories.RatingRepository;
import com.openclassrooms.poseidon.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;


    @Override
    public void saveRating(Rating rating) {
        ratingRepository.save(rating);
    }

    @Override
    public Optional<Rating> findById(Long id) {
        return ratingRepository.findById(id);
    }

    @Override
    public List<Rating> listAll() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating updateRating(Long id, Rating updatedRating) {
        Optional<Rating> existingRatingOpt = ratingRepository.findById(id);
        if (existingRatingOpt.isPresent()) {
            Rating existingRating = existingRatingOpt.get();
            existingRating.setFitchRating(updatedRating.getFitchRating());
            existingRating.setMoodysRating(updatedRating.getMoodysRating());
            existingRating.setSandPRating(updatedRating.getSandPRating());
            existingRating.setOrderNumber(updatedRating.getOrderNumber());
            return ratingRepository.save(existingRating);
        } else {
            throw new RuntimeException("Rating not found with id " + id);
        }
    }


    @Override
    public Rating updateRating(Long id) {
        return ratingRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        ratingRepository.deleteById(id);
    }
}
