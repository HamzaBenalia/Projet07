package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.RuleName;

import java.util.List;

public interface RuleNameService {

    void saveRuleName(RuleName ruleName);


    List<RuleName> listAll();


    RuleName getRuleName(Long id);


    void deleteRuleName(Long id);
}
