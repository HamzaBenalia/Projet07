package com.openclassrooms.poseidon.controllers;
import com.openclassrooms.poseidon.domain.RuleName;
import com.openclassrooms.poseidon.forms.RuleNameForm;
import com.openclassrooms.poseidon.services.RuleNameService;
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
public class RuleNameController {


    @Autowired
    private RuleNameService ruleNameService;

    @GetMapping("/ruleNameHomePage")
    public String viewHomePage(Model model) {
        model.addAttribute("ruleNameList", ruleNameService.listAll());
        return "ruleNameIndex"; // Cela renvoie vers la vue "ratings" pour afficher les résultats
    }

    @GetMapping("/showNewRuleNameForm")
    public String showNewRuleNameForm(Model model) {
        // create model attribute to bind form data
        RuleNameForm ruleNameForm = new RuleNameForm();
        model.addAttribute("ruleNameForm", ruleNameForm);
        return "newRuleName";
    }

    @PostMapping("/saveRuleName")
    public String saveRuleName(@Valid @ModelAttribute("ruleNameForm") RuleNameForm ruleNameForm,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "newRuleName";
        }

        try {
            RuleName ruleName = new RuleName();
            ruleName.setName(ruleNameForm.getName());
            ruleName.setJson(ruleNameForm.getJson());
            ruleName.setDescription(ruleNameForm.getDescription());
            ruleName.setTemplate(ruleNameForm.getTemplate());
            ruleName.setSqlPart(ruleNameForm.getSqlPart());
            ruleName.setSqlStr(ruleNameForm.getSqlStr());

            ruleNameService.saveRuleName(ruleName);

            return "redirect:/ruleNameHomePage";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "newRuleName";
        }
    }

    @GetMapping("/showFormForRuleNameUpdate/{id}")
    public String showFormForRuleNameUpdate(@PathVariable(value = "id") Long id, Model model) {
        try {
            Optional<RuleName> ruleNameOpt = ruleNameService.findById(id);
            if (ruleNameOpt.isPresent()) {
                RuleName ruleName = ruleNameOpt.get();
                model.addAttribute("ruleNameForm", ruleName);
                return "updateRuleName";
            } else {
                return "redirect:/ruleNameHomePage";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "redirect:/ruleNameHomePage";
        }
    }

    @PostMapping("/updateRuleName/{id}")
    public String updateRuleName(@PathVariable(value = "id") Long id, @Valid @ModelAttribute("ruleNameForm") RuleNameForm ruleNameForm, BindingResult result) {
        if (result.hasErrors()) {
            return "updateRuleName";
        }
        try {
            RuleName updatedRuleName = new RuleName();
            updatedRuleName.setName(ruleNameForm.getName());
            updatedRuleName.setJson(ruleNameForm.getJson());
            updatedRuleName.setDescription(ruleNameForm.getDescription());
            updatedRuleName.setTemplate(ruleNameForm.getTemplate());
            updatedRuleName.setSqlPart(ruleNameForm.getSqlPart());
            updatedRuleName.setSqlStr(ruleNameForm.getSqlStr());

            ruleNameService.updateRuleName(id, updatedRuleName);
            return "redirect:/ruleNameHomePage";
        } catch (Exception exception) {
            result.rejectValue("name", "", "error : " + exception.getMessage());
            return "updateRuleName";
        }
    }


    @GetMapping("/deleteRuleName/{id}")
    public String deleteRuleName(@PathVariable(value = "id") long id) {

        // call delete employee method
        this.ruleNameService.deleteRuleName(id);
        return "redirect:/ruleNameHomePage";
    }
}
