package com.openclassrooms.poseidon.controllers;
import com.openclassrooms.poseidon.domain.Trade;
import com.openclassrooms.poseidon.domain.Users;
import com.openclassrooms.poseidon.forms.TradeForm;
import com.openclassrooms.poseidon.repositories.TradeRepository;
import com.openclassrooms.poseidon.services.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static java.lang.reflect.Array.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

public class TradeControllerTest {


    @InjectMocks
    TradeController controller;

    @Mock
    TradeService tradeService;

    @Mock
    private TradeRepository tradeRepository;


    @Mock
    private BindingResult bindingResult;

    @Mock
    Model model;

    @Mock
    BindingResult result;


    @Test
    public void testViewHomePage() {
        when(tradeService.listAll()).thenReturn(new ArrayList<>());
        String view = controller.viewHomePage(model);
        verify(tradeService, times(1)).listAll();
        verify(model, times(1)).addAttribute("tradeList", tradeService.listAll());
        assertEquals("tradeIndex", view);
    }

    @Test
    public void testShowNewTradeForm() {
        String view = controller.showNewTradeForm(model);
        verify(model, times(1)).addAttribute(any(String.class), any(TradeForm.class));
        assertEquals("newTrade", view);
    }
    @Test
    public void saveTrade_whenTradeFormValid_returnsRedirectTradeHomePageView() {
        // Given
        TradeForm tradeForm = new TradeForm();
        tradeForm.setAccount("Account");
        tradeForm.setBuyQuantity("10");
        tradeForm.setType("Type");
        tradeForm.setTradeId("1");

        Trade trade = new Trade();
        trade.setAccount(tradeForm.getAccount());
        trade.setBuyQuantity(Double.valueOf(tradeForm.getBuyQuantity()));
        trade.setType(tradeForm.getType());

        when(bindingResult.hasErrors()).thenReturn(false);

        // When
        String viewName = controller.saveTrade(tradeForm, bindingResult, model);

        // Then
        assertEquals("redirect:/tradeHomePage", viewName);
    }

    @Test
    public void showFormForTradeUpdate_whenTradeExists_returnsUpdateTradeView() {
        // Given
        Trade trade = new Trade();
        trade.setAccount("TradeAccount");
        when(tradeService.findById(anyLong())).thenReturn(Optional.of(trade));

        // When
        String viewName = controller.showFormForTradeUpdate(1L, model);

        // Then
        assertEquals("updateTrade", viewName);
    }

    @Test
    public void updateTrade_whenNoErrorsInForm_returnsRedirectTradeHomePageView() {
        // Given
        TradeForm tradeForm = new TradeForm();
        tradeForm.setAccount("TradeAccount");
        tradeForm.setTradeId("1");
        tradeForm.setBuyQuantity("20");
        tradeForm.setType("highLevel");
        when(bindingResult.hasErrors()).thenReturn(false);

        // When
        String viewName = controller.updateTrade(1L, tradeForm, bindingResult);

        // Then
        assertEquals("redirect:/tradeHomePage", viewName);
    }





    @Test
    public void testSaveTrade_WithErrors() {
        TradeForm form = new TradeForm();
        when(result.hasErrors()).thenReturn(true);
        String view = controller.saveTrade(form, result, model);
        verify(tradeService, times(0)).saveTrade(any(Trade.class));
        assertEquals("newTrade", view);
    }

    @Test
    public void testSaveTradeWithException() {
        TradeForm tradeForm = new TradeForm();
        tradeForm.setAccount("TestAccount");
        tradeForm.setBuyQuantity("100");
        tradeForm.setType("TestType");

        when(result.hasErrors()).thenReturn(false);
        doThrow(new RuntimeException("Test exception")).when(tradeService).saveTrade(any(Trade.class));

        String view = controller.saveTrade(tradeForm, result, model);

        assertEquals("newTrade", view);
        verify(model).addAttribute(eq("errorMessage"), anyString());
    }




    @Test
    public void testDeleteTrade() {
        String view = controller.deleteTrade(1L);
        verify(tradeService, times(1)).deleteTrade(1L);
        assertEquals("redirect:/tradeHomePage", view);
    }
}

