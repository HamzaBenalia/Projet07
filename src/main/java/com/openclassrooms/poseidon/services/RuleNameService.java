package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.RuleName;

import java.util.List;
import java.util.Optional;

public interface RuleNameService {

    void saveRuleName(RuleName ruleName);


    List<RuleName> listAll();

    Optional<RuleName> findById(Long id);

    RuleName updateRuleName(Long id, RuleName updatedRuleName);


    RuleName updateRuleNameForm(Long id);


    void deleteRuleName(Long id);
}
