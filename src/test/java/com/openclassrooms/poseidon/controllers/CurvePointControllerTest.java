package com.openclassrooms.poseidon.controllers;
import com.openclassrooms.poseidon.domain.CurvePoint;
import com.openclassrooms.poseidon.forms.CurvePointFrom;
import com.openclassrooms.poseidon.services.CurvePointService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CurvePointControllerTest {

    @InjectMocks
    CurvePointController controller;

    @Mock
    CurvePointService curvePointService;

    @Mock
    Model model;

    @Mock
    BindingResult result;

    @Test
    public void testViewHomePage() {
        when(curvePointService.listAll()).thenReturn(new ArrayList<>());
        String view = controller.viewHomePage(model);
        verify(curvePointService, times(1)).listAll();
        verify(model, times(1)).addAttribute("curvePointList", curvePointService.listAll());
        assertEquals("curvePointIndex", view);
    }

    @Test
    public void testShowNewCurvePointForm() {
        String view = controller.showNewCurvePointForm(model);
        verify(model, times(1)).addAttribute(any(String.class), any(CurvePointFrom.class));
        assertEquals("newCurvePoint", view);
    }

    @Test
    public void testSaveCurvePoint() {
        CurvePointFrom form = new CurvePointFrom();
        form.setCurveId("22");
        form.setValue("30");
        form.setTerm("40");
        when(result.hasErrors()).thenReturn(false);
        String view = controller.saveCurvePoint(form, result, model);
        verify(curvePointService, times(1)).saveCurvePoint(any(CurvePoint.class));
        assertEquals("redirect:/curvePointHomePage", view);
    }

    @Test
    public void testSaveCurvePoint_WithErrors() {
        CurvePointFrom form = new CurvePointFrom();
        form.setCurveId("One");
        when(result.hasErrors()).thenReturn(true);
        String view = controller.saveCurvePoint(form, result, model);
        verify(curvePointService, times(0)).saveCurvePoint(any(CurvePoint.class));
        assertEquals("newCurvePoint", view);
    }

    @Test
    public void testShowFormForCurvePointUpdate() {
        CurvePoint curvePoint = new CurvePoint();
        when(curvePointService.getCurvePoint(any(Long.class))).thenReturn(curvePoint);
        String view = controller.showFormForCurvePointListUpdate(1L, model);
        verify(curvePointService, times(1)).getCurvePoint(1L);
        verify(model, times(1)).addAttribute("curvePoint", curvePoint);
        assertEquals("updateCurvePoint", view);
    }

    @Test
    public void testShowFormForCurvePointUpdate_NotFound() {
        when(curvePointService.getCurvePoint(any(Long.class))).thenReturn(null);
        String view = controller.showFormForCurvePointListUpdate(1L, model);
        verify(curvePointService, times(1)).getCurvePoint(1L);
        verify(model, times(0)).addAttribute(any(String.class), any());
        assertEquals("redirect:/curvePointHomePage", view);
    }

    @Test
    public void testDeleteCurvePoint() {
        String view = controller.deleteCurvePoint(1L);
        verify(curvePointService, times(1)).deleteCurvePoint(1L);
        assertEquals("redirect:/ruleNameHomePage", view);  // Assurez-vous que cette redirection est correcte
    }
}
