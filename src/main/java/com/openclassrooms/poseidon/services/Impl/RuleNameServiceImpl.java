package com.openclassrooms.poseidon.services.Impl;
import com.openclassrooms.poseidon.domain.RuleName;
import com.openclassrooms.poseidon.repositories.RuleNameRepository;
import com.openclassrooms.poseidon.services.RuleNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RuleNameServiceImpl implements RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;


    @Override
    public void saveRuleName(RuleName ruleName) {
        ruleNameRepository.save(ruleName);
    }

    @Override
    public Optional<RuleName> findById(Long id) {
        return ruleNameRepository.findById(id);
    }

    @Override
    public List<RuleName> listAll() {
        return ruleNameRepository.findAll();
    }

    @Override
    public RuleName updateRuleNameForm(Long id) {
        return ruleNameRepository.findById(id).get();
    }

    @Override
    public RuleName updateRuleName(Long id, RuleName updatedRuleName) {
        Optional<RuleName> existingRuleNameOpt = ruleNameRepository.findById(id);
        if (existingRuleNameOpt.isPresent()) {
            RuleName existingRuleName = existingRuleNameOpt.get();
            existingRuleName.setName(updatedRuleName.getName());
            existingRuleName.setJson(updatedRuleName.getJson());
            existingRuleName.setDescription(updatedRuleName.getDescription());
            existingRuleName.setTemplate(updatedRuleName.getTemplate());
            existingRuleName.setSqlPart(updatedRuleName.getSqlPart());
            existingRuleName.setSqlStr(updatedRuleName.getSqlStr());
            return ruleNameRepository.save(existingRuleName);
        } else {
            throw new RuntimeException("RuleName not found with id " + id);
        }
    }

    @Override
    public void deleteRuleName(Long id) {
        ruleNameRepository.deleteById(id);
    }
}
