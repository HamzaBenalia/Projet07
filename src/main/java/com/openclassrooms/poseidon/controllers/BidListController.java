package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.BidList;
import com.openclassrooms.poseidon.forms.BidListForm;
import com.openclassrooms.poseidon.services.BidListService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession httpSession = attr.getRequest().getSession();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasAdminRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ADMIN"));
        httpSession.setAttribute("hasAdminRole", hasAdminRole);
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
        try {

            BidList bidList = new BidList();
            bidList.setBidListId(Long.valueOf(bidListForm.getBidListId()));
            bidList.setAccount(bidListForm.getAccount());
            bidList.setType(bidListForm.getType());
            bidList.setBidQuantity(Double.valueOf(bidListForm.getBidQuantity()));

            bidListService.saveBidList(bidList);
            return "redirect:/bidListHomePage";
        } catch (Exception exception) {
            result.rejectValue("bidQuantity", "", "error : " + exception.getMessage());
            return "newBidList";
        }
    }


    @GetMapping("/showFormForBidListUpdate/{id}")
    public String showFormForBidListUpdate(@PathVariable(value = "id") Long id, Model model) {

        try {
            BidList bidList = bidListService.updateBidList(id);
            if (bidList != null) {
                model.addAttribute("bidListForm", bidList);
                return "updateBidList";
            } else {
                return "redirect:/bidListHomePage";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "redirect:/bidListHomePage";
        }
    }


    @GetMapping("/deleteBidList/{id}")
    public String deleteBidList(@PathVariable(value = "id") Long id) {
        this.bidListService.deleteBidList(id);
        return "redirect:/bidListHomePage";
    }
}
