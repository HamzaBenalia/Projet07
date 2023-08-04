package com.openclassrooms.poseidon.controllers;


import com.openclassrooms.poseidon.domain.Rating;
import com.openclassrooms.poseidon.forms.RatingForm;
import com.openclassrooms.poseidon.services.RatingService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@SpringBootTest
public class RatingControllerTest {


    @InjectMocks
    RatingController controller;

    @Mock
    RatingService ratingService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    Model model;

    @Mock
    BindingResult result;

    @Test
    public void testViewHomePage() {
        when(ratingService.listAll()).thenReturn(new ArrayList<>());
        String view = controller.viewHomePage(model);
        verify(ratingService, times(1)).listAll();
        verify(model, times(1)).addAttribute("ratingList", ratingService.listAll());
        assertEquals("ratingIndex", view);
    }

    @Test
    public void testShowNewRatingForm() {
        String view = controller.showNewRatingForm(model);
        verify(model, times(1)).addAttribute(any(String.class), any(RatingForm.class));
        assertEquals("newRating", view);
    }
    @Test
    public void testSaveRating() {
        RatingForm form = new RatingForm();
        // Définir les valeurs pour les attributs de form
        form.setOrderNumber("123");
        when(result.hasErrors()).thenReturn(false);
        String view = controller.saveRating(form, result, model);

        verify(ratingService, times(1)).saveRating(any(Rating.class));
        assertEquals("redirect:/ratingHomePage", view);
    }

    @Test
    public void testSaveRatingWithException() {
        RatingForm ratingForm = new RatingForm();
        // set form fields here

        when(result.hasErrors()).thenReturn(false);
        doThrow(new RuntimeException("Test exception")).when(ratingService).saveRating(any(Rating.class));

        String view = controller.saveRating(ratingForm, result, model);

        assertEquals("newRating", view);
        verify(model).addAttribute(eq("errorMessage"), anyString());
    }

    @Test
    public void showFormForRatingListUpdate_whenRatingExists_returnsUpdateRatingView() {
        // Given
        Rating rating = new Rating();
        rating.setMoodysRating("MoodysRating");
        when(ratingService.findById(anyLong())).thenReturn(Optional.of(rating));

        // When
        String viewName = controller.showFormForRatingListUpdate(1L, model);

        // Then
        assertEquals("updateRating", viewName);
    }

    @Test
    public void updateRating_whenNoErrorsInForm_returnsRedirectRatingHomePageView() {
        // Given
        RatingForm ratingForm = new RatingForm();
        ratingForm.setFitchRating("FitchRating");
        ratingForm.setOrderNumber("2");
        ratingForm.setMoodysRating("5");
        ratingForm.setId("1L");
        ratingForm.setSandPRating("5");

        // When
        String viewName = controller.updateRating(1L, ratingForm, bindingResult);

        // Then
        assertEquals("redirect:/ratingHomePage", viewName);
    }



    @Test
    public void testDeleteRating() {
        String view = controller.deleteRating(1L);
        verify(ratingService, times(1)).delete(1L);
        assertEquals("redirect:/ratingHomePage",view);
    }

    @Test
    public void testSaveRating_WithErrors() {
        RatingForm form = new RatingForm();
        when(result.hasErrors()).thenReturn(true);
        String view = controller.saveRating(form, result, model);
        verify(ratingService, times(0)).saveRating(any(Rating.class));
        assertEquals("newRating", view);
    }

}
