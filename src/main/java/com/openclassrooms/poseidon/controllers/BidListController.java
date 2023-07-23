package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.BidList;
import com.openclassrooms.poseidon.forms.BidListForm;
import com.openclassrooms.poseidon.services.BidListService;
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
public class BidListController {

    @Autowired
    private BidListService bidListService;


    @GetMapping("/bidListHomePage")
    public String bidListHomePage(Model model) {
        return "redirect:/";
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("bidListList", bidListService.listAll());
        return "index";
    }

    @GetMapping("/showNewBidListForm")
    public String showNewBidListForm(Model model) {
        // create model attribute to bind form data
        BidListForm bidListForm = new BidListForm();
        model.addAttribute("bidListForm", bidListForm);
        return "newBidList";
    }

    @PostMapping("/saveBidList")
    public String saveBidList(@Valid @ModelAttribute("bidListForm") BidListForm bidListForm,
                              BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "newBidList";
        }

        BidList bidList = new BidList();

        // Conversion explicite de bidListId en Long
        bidList.setBidListId(Long.valueOf(bidListForm.getBidListId()));

        bidList.setAccount(bidListForm.getAccount());
        bidList.setType(bidListForm.getType());

        // Conversion explicite de bidQuantity en Double
        bidList.setBidQuantity(Double.valueOf(bidListForm.getBidQuantity()));

        bidListService.saveBidList(bidList);
        return "redirect:/bidListHomePage";
    }

    @GetMapping("/showFormForBidListUpdate/{id}")
    public String showFormForBidListUpdate(@PathVariable(value = "id") Long id, Model model) {

        BidList bidList = bidListService.getBidList(id);
        model.addAttribute("bidListForm", bidList);
        return "updateBidList";
    }

    @GetMapping("/deleteBidList/{id}")
    public String deleteCurvePoint(@PathVariable(value = "id") Long id) {

        // call delete employee method
        this.bidListService.deleteBidList(id);
        return "redirect:/bidListHomePage";
    }
}
