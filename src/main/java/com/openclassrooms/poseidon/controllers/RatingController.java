package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.Rating;
import com.openclassrooms.poseidon.forms.RatingForm;
import com.openclassrooms.poseidon.services.RatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping("/ratingHomePage")
    public String viewHomePage(Model model) {
        RatingForm ratingList = new RatingForm();
        model.addAttribute("ratingList", ratingService.listAll());
        return "ratingIndex"; // Cela renvoie vers la vue "ratings" pour afficher les résultats
    }

    @GetMapping("/showNewRatingForm")
    public String showNewRatingForm(Model model) {
        // create model attribute to bind form data
        RatingForm ratingForm = new RatingForm();
        model.addAttribute("ratingForm", ratingForm);
        return "newRating";
    }

    @PostMapping("/saveRating")
    public String saveRating(@Valid @ModelAttribute("ratingForm") RatingForm ratingForm,
                             BindingResult result, Model model) {

        if(result.hasErrors()){
            return "newRating";
        }
        Rating rating = new Rating();
        rating.setFitchRating(ratingForm.getFitchRating());
        rating.setMoodysRating(ratingForm.getMoodysRating());
        rating.setSandPRating(ratingForm.getSandPRating());
        rating.setOrderNumber(Long.valueOf(ratingForm.getOrderNumber()));
        ratingService.saveRating(rating);
        return "redirect:/ratingHomePage";
    }

    @GetMapping("/showFormForRatingListUpdate/{id}")
    public String showFormForRatingListUpdate(@PathVariable(value = "id") long id, Model model) {

        Rating rating = ratingService.get(id);
        model.addAttribute("rating", rating);
        return "updateRating";
    }

    @GetMapping("/deleteRatingList/{id}")
    public String deleteRating(@PathVariable(value = "id") long id) {

        // call delete employee method
        this.ratingService.delete(id);
        return "redirect:/ratingHomePage";
    }
}
