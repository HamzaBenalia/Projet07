package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.RuleName;
import com.openclassrooms.poseidon.repositories.RuleNameRepository;
import com.openclassrooms.poseidon.services.Impl.RuleNameServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {


    @InjectMocks
    private RuleNameServiceImpl ruleNameServiceImpl;

    @Mock
    private RuleNameRepository ruleNameRepository;


    @Captor
    private ArgumentCaptor<RuleName> RuleNameArgumentCaptor;


    @Test
    public void testSaveRuleName() {
        RuleName ruleName = new RuleName(12345678988520L, "Hamza", "hamza", "ben", "high", "low", "good");

        ruleNameServiceImpl.saveRuleName(ruleName);

        verify(ruleNameRepository).save(RuleNameArgumentCaptor.capture());
        RuleName capturedRuleName = RuleNameArgumentCaptor.getValue();
        assertThat(capturedRuleName).isEqualTo(ruleName);
    }


    @Test
    public void testListAllRuleName() {
        // Arrange
        List<RuleName> expectedRuleName = new ArrayList<>();
        expectedRuleName.add(new RuleName(12345678988520L, "Hamza", "hamza", "ben", "high", "low", "good"));
        expectedRuleName.add(new RuleName(12345678988520L, "Eric", "Eric", "john", "low", "high", "bad"));

        when(ruleNameRepository.findAll()).thenReturn(expectedRuleName);

        // Act
        List<RuleName> actualUsers = ruleNameServiceImpl.listAll();

        // Assert
        verify(ruleNameRepository).findAll();
        assertThat(actualUsers).isEqualTo(expectedRuleName);
    }


    @Test
    public void testGetRuleName() {
        // Arrange
        Long id = 1234567890123456789L;
        RuleName expectedRuleName = new RuleName(12345678988520L, "Hamza", "hamza", "ben", "high", "low", "good");
        Optional<RuleName> expectedOptional = Optional.of(expectedRuleName);

        when(ruleNameRepository.findById(id)).thenReturn(expectedOptional);

        // Act
        RuleName actualRuleName = ruleNameServiceImpl.updateRuleName(id);

        // Assert
        verify(ruleNameRepository).findById(id);
        assertThat(actualRuleName).isEqualTo(expectedRuleName);
    }

    @Test
    public void testDelete() {
        // Arrange
        Long id = 1234567890123456789L;

        RuleName expectedRuleName = new RuleName(id, "Hamza", "hamza", "ben", "high", "low", "good");


        // Act
        ruleNameServiceImpl.deleteRuleName(expectedRuleName.getId());

        // Assert
        verify(ruleNameRepository, times(1)).deleteById(id);

    }
    @Test
    public void testDeleteShouldReturnZeroTimes() {
        // Arrange
        Long id = 1234567890123456789L;

        RuleName expectedRuleName = new RuleName(12345678988520L, "Hamza", "hamza", "ben", "high", "low", "good");


        // Act
        ruleNameServiceImpl.deleteRuleName(expectedRuleName.getId());

        // Assert
        verify(ruleNameRepository, times(0)).deleteById(id);

    }

}
