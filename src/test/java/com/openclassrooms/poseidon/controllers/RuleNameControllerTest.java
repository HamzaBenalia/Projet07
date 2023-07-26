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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RuleNameControllerTest {


    @InjectMocks
    RuleNameController controller;

    @Mock
    RuleNameService ruleNameService;

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
    public void testSaveRuleName_WithErrors() {
        RuleNameForm form = new RuleNameForm();
        when(result.hasErrors()).thenReturn(true);
        String view = controller.saveRuleName(form, result, model);
        verify(ruleNameService, times(0)).saveRuleName(any(RuleName.class));
        assertEquals("newRuleName", view);
    }

    @Test
    public void testShowFormForRuleNameUpdate_RuleNameFound() {
        RuleName ruleName = new RuleName();
        when(ruleNameService.getRuleName(any(Long.class))).thenReturn(ruleName);

        String view = controller.showFormForRuleNameUpdate(1L, model);

        verify(ruleNameService, times(1)).getRuleName(1L);
        verify(model, times(1)).addAttribute("ruleName", ruleName);

        assertEquals("updateRuleName", view);
    }

    @Test
    public void testShowFormForRuleNameUpdate_RuleNameNotFound() {
        when(ruleNameService.getRuleName(any(Long.class))).thenReturn(null);

        String view = controller.showFormForRuleNameUpdate(1L, model);

        verify(ruleNameService, times(1)).getRuleName(1L);
        verify(model, times(0)).addAttribute(any(String.class), any());

        assertEquals("redirect:/ruleNameHomePage", view);
    }


    @Test
    public void testDeleteRuleName() {
        String view = controller.deleteRuleName(1L);
        verify(ruleNameService, times(1)).deleteRuleName(1L);
        assertEquals("redirect:/ruleNameHomePage", view);
    }
}
