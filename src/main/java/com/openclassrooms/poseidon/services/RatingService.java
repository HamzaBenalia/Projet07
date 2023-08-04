package com.openclassrooms.poseidon.services;
import com.openclassrooms.poseidon.domain.Rating;
import java.util.List;
import java.util.Optional;

public interface RatingService {

    void saveRating(Rating rating);

    List<Rating> listAll();

    Optional<Rating> findById(Long id);

    Rating updateRating(Long id, Rating updatedRating);

    void delete(Long id);

    Rating updateRating(Long id);
}
