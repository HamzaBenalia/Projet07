package com.openclassrooms.poseidon.controllers;
import com.openclassrooms.poseidon.domain.Trade;
import com.openclassrooms.poseidon.forms.TradeForm;
import com.openclassrooms.poseidon.services.TradeService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TradeControllerTest {

    @InjectMocks
    TradeController controller;

    @Mock
    TradeService tradeService;

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
    public void testSaveTrade_Success() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        TradeForm tradeForm = new TradeForm();
        tradeForm.setBuyQuantity("10");  // Assurez-vous que la quantité d'achat n'est pas null

        String view = controller.saveTrade(tradeForm, bindingResult, model);

        ArgumentCaptor<Trade> tradeCaptor = ArgumentCaptor.forClass(Trade.class);
        verify(tradeService).saveTrade(tradeCaptor.capture());

        Trade capturedTrade = tradeCaptor.getValue();
        assertEquals(10.0, capturedTrade.getBuyQuantity(), 0.01);  // Vous pouvez vérifier la quantité d'achat ici

        assertEquals("redirect:/tradeHomePage", view);
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
    public void testShowFormForTradeUpdate() {
        Trade trade = new Trade();
        when(tradeService.getTrade(any(Long.class))).thenReturn(trade);
        String view = controller.showFormForTradeUpdate(1L, model);
        verify(tradeService, times(1)).getTrade(1L);
        verify(model, times(1)).addAttribute("trade", trade);
        assertEquals("tradeForm", view);
    }

    @Test
    public void testShowFormForTradeUpdate_NotFound() {
        when(tradeService.getTrade(any(Long.class))).thenReturn(null);
        String view = controller.showFormForTradeUpdate(1L, model);
        verify(tradeService, times(1)).getTrade(1L);
        verify(model, times(0)).addAttribute(any(String.class), any());
        assertEquals("redirect:/tradeHomePage", view);
    }

    @Test
    public void testDeleteTrade() {
        String view = controller.deleteTrade(1L);
        verify(tradeService, times(1)).deleteTrade(1L);
        assertEquals("redirect:/tradeHomePage", view);
    }
    @Test
    public void testShowFormForTradeUpdateNotFound() {
        when(tradeService.getTrade(any(Long.class))).thenReturn(null);

        String view = controller.showFormForTradeUpdate(1L, model);

        verify(tradeService, times(1)).getTrade(1L);
        verify(model, times(0)).addAttribute(any(String.class), any());

        assertEquals("redirect:/tradeHomePage", view);
    }
}
