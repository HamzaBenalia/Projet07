package com.openclassrooms.poseidon.services.Impl;

import com.openclassrooms.poseidon.domain.RuleName;
import com.openclassrooms.poseidon.repositories.RuleNameRepository;
import com.openclassrooms.poseidon.services.RuleNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleNameServiceImpl implements RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;


    @Override
    public void saveRuleName(RuleName ruleName) {
        ruleNameRepository.save(ruleName);
    }

    @Override
    public List<RuleName> listAll() {
        return ruleNameRepository.findAll();
    }

    @Override
    public RuleName updateRuleName(Long id) {
        return ruleNameRepository.findById(id).get();
    }

    @Override
    public void deleteRuleName(Long id) {
        ruleNameRepository.deleteById(id);
    }
}
