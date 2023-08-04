package com.openclassrooms.poseidon.controllers;
import com.openclassrooms.poseidon.domain.RuleName;
import com.openclassrooms.poseidon.forms.RuleNameForm;
import com.openclassrooms.poseidon.services.RuleNameService;
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
public class RuleNameControllerTest {


    @InjectMocks
    RuleNameController controller;

    @Mock
    RuleNameService ruleNameService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    Model model;

    @Mock
    BindingResult result;

    @Test
    public void testViewHomePage() {
        when(ruleNameService.listAll()).thenReturn(new ArrayList<>());
        String view = controller.viewHomePage(model);
        verify(ruleNameService, times(1)).listAll();
        verify(model, times(1)).addAttribute("ruleNameList", ruleNameService.listAll());
        assertEquals("ruleNameIndex", view);
    }

    @Test
    public void testShowNewRuleNameForm() {
        String view = controller.showNewRuleNameForm(model);
        verify(model, times(1)).addAttribute(any(String.class), any(RuleNameForm.class));
        assertEquals("newRuleName", view);
    }

    @Test
    public void testSaveRuleName() {
        RuleNameForm form = new RuleNameForm();
        when(result.hasErrors()).thenReturn(false);
        String view = controller.saveRuleName(form, result, model);
        verify(ruleNameService, times(1)).saveRuleName(any(RuleName.class));
        assertEquals("redirect:/ruleNameHomePage", view);
    }

    @Test
    public void updateRuleName_whenResultHasErrors_returnsUpdateRuleNameView() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        String viewName = controller.updateRuleName(1L, new RuleNameForm(), bindingResult);

        // Then
        assertEquals("updateRuleName", viewName);
    }

    @Test
    public void updateRuleName_whenResultHasNoErrorsAndNoExceptions_returnsRedirectRuleNameHomePageView() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(false);

        // When
        String viewName = controller.updateRuleName(1L, new RuleNameForm(), bindingResult);

        // Then
        assertEquals("redirect:/ruleNameHomePage", viewName);
    }

    @Test
    public void showFormForRuleNameUpdate_whenRuleNameExists_returnsUpdateRuleNameView() {
        // Given
        RuleName ruleName = new RuleName();
        ruleName.setName("RuleName1");
        when(ruleNameService.findById(anyLong())).thenReturn(Optional.of(ruleName));

        // When
        String viewName = controller.showFormForRuleNameUpdate(1L, model);

        // Then
        assertEquals("updateRuleName", viewName);
    }

    @Test
    public void testSaveRuleName_WithErrors() {
        RuleNameForm form = new RuleNameForm();
        when(result.hasErrors()).thenReturn(true);
        String view = controller.saveRuleName(form, result, model);
        verify(ruleNameService, times(0)).saveRuleName(any(RuleName.class));
        assertEquals("newRuleName", view);
    }

    @Test
    public void testSaveRuleNameWithException() {
        RuleNameForm ruleNameForm = new RuleNameForm();
        // set form fields here

        when(result.hasErrors()).thenReturn(false);
        doThrow(new RuntimeException("Test exception")).when(ruleNameService).saveRuleName(any(RuleName.class));

        String view = controller.saveRuleName(ruleNameForm, result, model);

        assertEquals("newRuleName", view);
        verify(model).addAttribute(eq("errorMessage"), anyString());
    }

    @Test
    public void testDeleteRuleName() {
        String view = controller.deleteRuleName(1L);
        verify(ruleNameService, times(1)).deleteRuleName(1L);
        assertEquals("redirect:/ruleNameHomePage", view);
    }
}
