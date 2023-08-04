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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CurvePointControllerTest {

    @InjectMocks
    CurvePointController controller;

    @Mock
    private BindingResult bindingResult;

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
    public void showFormForCurvePointListUpdate_whenCurvePointPresent_returnsUpdateCurvePointView() {
        // Given
        when(curvePointService.findById(anyLong())).thenReturn(Optional.of(new CurvePoint()));

        // When
        String viewName = controller.showFormForCurvePointListUpdate(1L, model);

        // Then
        assertEquals("updateCurvePoint", viewName);
    }

    @Test
    public void updateCurvePoint_whenFormHasErrors_returnsUpdateCurvePointView() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        String viewName = controller.updateCurvePoint(1L, new CurvePointFrom(), bindingResult);

        // Then
        assertEquals("updateCurvePoint", viewName);
    }


    @Test
    public void updateCurvePoint_whenNoErrorsAndExceptionOccurs_returnsUpdateCurvePointView() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(false);
        when(curvePointService.updateCurvePoint(anyLong(), any(CurvePoint.class))).thenThrow(RuntimeException.class);

        // When
        String viewName = controller.updateCurvePoint(1L, new CurvePointFrom(), bindingResult);

        // Then
        assertEquals("updateCurvePoint", viewName);
    }

    @Test
    public void showFormForCurvePointListUpdate_whenCurvePointAbsent_returnsRedirectCurvePointHomePageView() {
        // Given
        when(curvePointService.findById(anyLong())).thenReturn(Optional.empty());

        // When
        String viewName = controller.showFormForCurvePointListUpdate(1L, model);

        // Then
        assertEquals("redirect:/curvePointHomePage", viewName);
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
    public void testSaveCurvePointWithException() {
        CurvePointFrom curvePointForm = new CurvePointFrom();
        // set form fields here

        when(result.hasErrors()).thenReturn(false);
        doThrow(new RuntimeException("Test exception")).when(curvePointService).saveCurvePoint(any(CurvePoint.class));

        String view = controller.saveCurvePoint(curvePointForm, result, model);

        assertEquals("newCurvePoint", view);
        verify(model).addAttribute(eq("errorMessage"), anyString());
    }



    @Test
    public void testDeleteCurvePoint() {
        String view = controller.deleteCurvePoint(1L);
        verify(curvePointService, times(1)).deleteCurvePoint(1L);
        assertEquals("redirect:/curvePointHomePage", view);  // Assurez-vous que cette redirection est correcte
    }
}
