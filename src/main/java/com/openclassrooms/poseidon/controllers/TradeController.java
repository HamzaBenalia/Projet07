package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.Trade;
import com.openclassrooms.poseidon.forms.TradeForm;
import com.openclassrooms.poseidon.services.TradeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class TradeController {

    @Autowired
    private TradeService tradeService;


    @GetMapping("/tradeHomePage")
    public String viewHomePage(Model model) {

        model.addAttribute("tradeList", tradeService.listAll());
        return "tradeIndex"; // Cela renvoie vers la vue "ratings" pour afficher les résultats
    }

    @GetMapping("/showNewTradeForm")
    public String showNewTradeForm(Model model) { // TradeForm instead of Trade
        // create model attribute to bind form data
        TradeForm tradeForm = new TradeForm();
        model.addAttribute("tradeForm", tradeForm);
        return "newTrade";
    }

    @PostMapping("/saveTrade")
    public String saveTrade(@Valid @ModelAttribute("tradeForm") TradeForm tradeForm,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "newTrade";
        }

        try {
            Trade trade = new Trade();
//            trade.setTradeId(Long.valueOf(tradeForm.getTradeId()));
            trade.setAccount(tradeForm.getAccount());
            trade.setBuyQuantity(Double.valueOf(tradeForm.getBuyQuantity()));
            trade.setType(tradeForm.getType());

            tradeService.saveTrade(trade);

            return "redirect:/tradeHomePage";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "newTrade";
        }
    }

    @GetMapping("/showFormForTradeUpdate/{tradeId}")
    public String showFormForTradeUpdate(@PathVariable(value = "tradeId") Long id, Model model) {
        try {
            Optional<Trade> tradeOpt = tradeService.findById(id);
            if (tradeOpt.isPresent()) {
                Trade trade = tradeOpt.get();
                model.addAttribute("tradeForm", trade);
                return "updateTrade";
            } else {
                return "redirect:/tradeHomePage";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "redirect:/tradeHomePage";
        }
    }

    @PostMapping("/updateTrade/{tradeId}")
    public String updateTrade(@PathVariable(value = "tradeId") Long tradeId, @Valid @ModelAttribute("tradeForm") TradeForm tradeForm, BindingResult result) {
        if (result.hasErrors()) {
            return "updateTrade";
        }
        try {
            Trade updatedTrade = new Trade();
//            updatedTrade.setTradeId(Long.valueOf(tradeForm.getTradeId()));
            updatedTrade.setAccount(tradeForm.getAccount());
            updatedTrade.setBuyQuantity(Double.valueOf(tradeForm.getBuyQuantity()));
            updatedTrade.setType(tradeForm.getType());

            tradeService.updateTrade(tradeId, updatedTrade);
            return "redirect:/tradeHomePage";
        } catch (Exception exception) {
            result.rejectValue("account", "", "error : " + exception.getMessage());
            return "updateTrade";
        }
    }


    @GetMapping("/deleteTrade/{tradeId}")
    public String deleteTrade(@PathVariable(value = "tradeId") Long tradeId) {

        // call delete employee method
        this.tradeService.deleteTrade(tradeId);
        return "redirect:/tradeHomePage";
    }
}

