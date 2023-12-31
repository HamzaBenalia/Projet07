package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.Rating;
import com.openclassrooms.poseidon.domain.RuleName;
import com.openclassrooms.poseidon.repositories.RatingRepository;
import com.openclassrooms.poseidon.services.Impl.RatingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @InjectMocks
    private RatingServiceImpl ratingServiceImpl;

    @Mock
    private RatingRepository ratingRepository;


    @Captor
    private ArgumentCaptor<Rating> RatingArgumentCaptor;


    @Test
    public void testSaveRating() {
        Rating rating = new Rating(123456789123L, "high", "high", "low", 123456789123456L);

        ratingServiceImpl.saveRating(rating);

        verify(ratingRepository).save(RatingArgumentCaptor.capture());
        Rating capturedRating = RatingArgumentCaptor.getValue();
        assertThat(capturedRating).isEqualTo(rating);
    }


    @Test
    public void testListAllRating() {
        // Arrange
        List<Rating> expectedRating = new ArrayList<>();
        expectedRating.add(new Rating(123456789123L, "high", "high", "low", 123456789123456L));
        expectedRating.add(new Rating(987654321123L, "Low", "Low", "low", 98765432112336L));

        when(ratingRepository.findAll()).thenReturn(expectedRating);

        // Act
        List<Rating> actualRating = ratingServiceImpl.listAll();

        // Assert
        verify(ratingRepository).findAll();
        assertThat(actualRating).isEqualTo(expectedRating);
    }


    @Test
    public void testGetRuleName() {
        // Arrange
        Long id = 1234567890123456789L;
        Rating expectedRating = new Rating(123456789123L, "high", "high", "low", 123456789123456L);
        Optional<Rating> expectedOptional = Optional.of(expectedRating);

        when(ratingRepository.findById(id)).thenReturn(expectedOptional);

        // Act
        Rating actualRating = ratingServiceImpl.updateRating(id);

        // Assert
        verify(ratingRepository).findById(id);
        assertThat(actualRating).isEqualTo(expectedRating);
    }


    @Test
    public void updateRating_whenRatingExists_thenUpdatesAndReturnsRating() {
        // Given
        Long id = 1L;
        Rating existingRating = new Rating();
        existingRating.setFitchRating("existingRating");
        Rating updatedRating = new Rating();
        updatedRating.setFitchRating("updatedRating");

        when(ratingRepository.findById(id)).thenReturn(Optional.of(existingRating));
        when(ratingRepository.save(any(Rating.class))).thenReturn(updatedRating);

        // When
        Rating result = ratingServiceImpl.updateRating(id, updatedRating);

        // Then
        assertEquals(updatedRating.getFitchRating(), result.getFitchRating());
        verify(ratingRepository, times(1)).findById(id);
        verify(ratingRepository, times(1)).save(any(Rating.class));
    }

    @Test
    public void findById_whenRatingExists_returnsRating() {
        // Given
        Rating rating = new Rating();
        rating.setMoodysRating("MoodysRating");
        when(ratingRepository.findById(anyLong())).thenReturn(Optional.of(rating));

        // When
        Optional<Rating> result = ratingServiceImpl.findById(1L);

        // Then
        assertTrue(result.isPresent());
    }

    @Test
    public void updateRating_whenRatingNotExists_thenThrowsException() {
        // Given
        Long id = 1L;
        Rating updatedRating = new Rating();
        updatedRating.setFitchRating("updatedRating");

        when(ratingRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(RuntimeException.class, () -> ratingServiceImpl.updateRating(id, updatedRating));
        verify(ratingRepository, times(1)).findById(id);
    }

    @Test
    public void testDelete() {
        // Arrange
        Long id = 1234567890123456789L;
        Rating expectedRating = new Rating(id, "high", "high", "low", 123456789123456L);
        // Act
        ratingServiceImpl.delete(expectedRating.getId());

        // Assert
        verify(ratingRepository, times(1)).deleteById(id);

    }

    @Test
    public void testDeleteShouldReturnZeroTimes() {
        // Arrange
        Long id = 1234567890123456789L;
        Rating expectedRating = new Rating(123456789123456L, "high", "high", "low", 123456789123456L);
        // Act
        ratingServiceImpl.delete(expectedRating.getId());

        // Assert
        verify(ratingRepository, times(0)).deleteById(id);
    }
}
