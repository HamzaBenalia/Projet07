package com.openclassrooms.poseidon.controllers;
import com.openclassrooms.poseidon.domain.CurvePoint;
import com.openclassrooms.poseidon.forms.CurvePointFrom;
import com.openclassrooms.poseidon.services.CurvePointService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class CurvePointController {

    @Autowired
    private CurvePointService curvePointService;


    @GetMapping("/curvePointHomePage")
    public String viewHomePage(Model model) {

        model.addAttribute("curvePointList", curvePointService.listAll());
        return "curvePointIndex"; // Cela renvoie vers la vue "ratings" pour afficher les résultats
    }

    @GetMapping("/showNewCurvePointForm")
    public String showNewCurvePointForm(Model model) {
        // create model attribute to bind form data
        CurvePointFrom curvePointForm = new CurvePointFrom();
        model.addAttribute("curvePointForm", curvePointForm);
        return "newCurvePoint";
    }

    @PostMapping("/saveCurvePoint")
    public String saveCurvePoint(@Valid @ModelAttribute("curvePointForm") CurvePointFrom curvePointForm,
                                 BindingResult result,
                                 Model model) {
        if (result.hasErrors()) {
            return "newCurvePoint";
        }

        try {
            CurvePoint curvePoint = new CurvePoint();
            curvePoint.setCurveId(Long.valueOf(curvePointForm.getCurveId()));
            curvePoint.setValue(Double.valueOf(curvePointForm.getValue()));
            curvePoint.setTerm(Double.valueOf(curvePointForm.getTerm()));
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            curvePoint.setAsOfDate(now);
            curvePoint.setCreationDate(now);

            curvePointService.saveCurvePoint(curvePoint);

            return "redirect:/curvePointHomePage";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "newCurvePoint";
        }
    }

    @GetMapping("/showFormForCurvePointUpdate/{id}")
    public String showFormForCurvePointListUpdate(@PathVariable(value = "id") Long id, Model model) {
        try {
            Optional<CurvePoint> curvePointOpt = curvePointService.findById(id);
            if (curvePointOpt.isPresent()) {
                CurvePoint curvePoint = curvePointOpt.get();
                model.addAttribute("curvePointForm", curvePoint);
                return "updateCurvePoint";
            } else {
                return "redirect:/curvePointHomePage";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "redirect:/curvePointHomePage";
        }
    }

    @PostMapping("/updateCurvePoint/{id}")
    public String updateCurvePoint(@PathVariable(value = "id") Long id, @Valid @ModelAttribute("curvePointForm") CurvePointFrom curvePointForm, BindingResult result) {
        if (result.hasErrors()) {
            return "updateCurvePoint";
        }
        try {
            CurvePoint updatedCurvePoint = new CurvePoint();
            updatedCurvePoint.setCurveId(Long.valueOf(curvePointForm.getCurveId()));
            updatedCurvePoint.setValue(Double.valueOf(curvePointForm.getValue()));
            updatedCurvePoint.setTerm(Double.valueOf(curvePointForm.getTerm()));
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            updatedCurvePoint.setAsOfDate(now);
            updatedCurvePoint.setCreationDate(now);

            curvePointService.updateCurvePoint(id, updatedCurvePoint);
            return "redirect:/curvePointHomePage";
        } catch (Exception exception) {
            result.rejectValue("value", "", "error : " + exception.getMessage());
            return "updateCurvePoint";
        }
    }


    @GetMapping("/deleteCurvePoint/{id}")
    public String deleteCurvePoint(@PathVariable(value = "id") long id) {

        // call delete employee method
        this.curvePointService.deleteCurvePoint(id);
        return "redirect:/curvePointHomePage";
    }
}
