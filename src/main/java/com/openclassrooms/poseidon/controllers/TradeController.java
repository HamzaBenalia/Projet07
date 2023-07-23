package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.Trade;
import com.openclassrooms.poseidon.services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String showNewTradeForm(Model model, Trade trade) { // TradeForm instead of Trade
        // create model attribute to bind form data

        model.addAttribute("trade", trade);
        return "newTrade";
    }

    @PostMapping("/saveTrade")
    public String saveTrade(@ModelAttribute("tradeForm") Trade trade,
                            BindingResult result, Model model) {
        // setters and getters
        tradeService.saveTrade(trade);
        return "redirect:/tradeHomePage";
    }


    @GetMapping("/showFormForTradeUpdate/{tradeId}")
    public String showFormForTradeUpdate(@PathVariable(value = "tradeId") Long tradeId, Model model) {

        Trade trade = tradeService.getTrade(tradeId);
        model.addAttribute("trade", trade);
        return "updateTrade";
    }

    @GetMapping("/deleteTrade/{tradeId}")
    public String deleteTrade(@PathVariable(value = "tradeId") Long tradeId) {

        // call delete employee method
        this.tradeService.deleteTrade(tradeId);
        return "redirect:/tradeHomePage";
    }
}

