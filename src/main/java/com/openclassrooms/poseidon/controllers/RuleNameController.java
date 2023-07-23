package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.RuleName;
import com.openclassrooms.poseidon.forms.RuleNameForm;
import com.openclassrooms.poseidon.services.RuleNameService;
import org.h2.engine.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String showNewRatingForm(Model model) {
        // create model attribute to bind form data
        RuleNameForm ruleNameForm = new RuleNameForm();
        model.addAttribute("ruleNameForm", ruleNameForm);
        return "newRuleName";
    }

    @PostMapping("/saveRuleName")
    public String saveRating(@ModelAttribute("ruleNameForm") RuleNameForm ruleNameForm,
                             BindingResult result, Model model) {
        if(result.hasErrors()){
            return "newRuleName";
        }
        RuleName ruleName = new RuleName();
        ruleName.setId(Long.valueOf(ruleNameForm.getId()));
        ruleName.setName(ruleNameForm.getName());
        ruleName.setJson(ruleNameForm.getJson());
        ruleName.setDescription(ruleNameForm.getDescription());
        ruleName.setTemplate(ruleNameForm.getTemplate());
        ruleName.setSqlPart(ruleNameForm.getSqlPart());
        ruleName.setSqlStr(ruleNameForm.getSqlStr());
        ruleNameService.saveRuleName(ruleName);
        return "redirect:/ruleNameHomePage";
    }

    @GetMapping("/showFormForRuleNameUpdate/{id}")
    public String showFormForRatingListUpdate(@PathVariable(value = "id") long id, Model model) {

        RuleName ruleName = ruleNameService.getRuleName(id);
        model.addAttribute("ruleName", ruleName);
        return "updateRuleName";
    }

    @GetMapping("/deleteRuleName/{id}")
    public String deleteRating(@PathVariable(value = "id") long id) {

        // call delete employee method
        this.ruleNameService.deleteRuleName(id);
        return "redirect:/ruleNameHomePage";
    }
}
