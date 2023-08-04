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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    public void updateRuleName_whenRuleNameExists_thenUpdatesAndReturnsRuleName() {
        // Given
        Long id = 1L;
        RuleName existingRuleName = new RuleName();
        existingRuleName.setName("existingName");
        RuleName updatedRuleName = new RuleName();
        updatedRuleName.setName("updatedName");

        when(ruleNameRepository.findById(id)).thenReturn(Optional.of(existingRuleName));
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(updatedRuleName);

        // When
        RuleName result = ruleNameServiceImpl.updateRuleName(id, updatedRuleName);

        // Then
        assertEquals(updatedRuleName.getName(), result.getName());
        verify(ruleNameRepository, times(1)).findById(id);
        verify(ruleNameRepository, times(1)).save(any(RuleName.class));
    }
    @Test
    public void updateRuleNameForm_whenRuleNameExists_returnsRuleName() {
        // Given
        Long id = 1L;
        RuleName ruleName = new RuleName();
        ruleName.setId(id);
        when(ruleNameRepository.findById(id)).thenReturn(Optional.of(ruleName));

        // When
        RuleName result = ruleNameServiceImpl.updateRuleNameForm(id);

        // Then
        assertEquals(ruleName, result);
    }

    @Test
    public void findById_whenRuleNameExists_returnsRuleName() {
        // Given
        RuleName ruleName = new RuleName();
        ruleName.setName("Name");
        when(ruleNameRepository.findById(anyLong())).thenReturn(Optional.of(ruleName));

        // When
        Optional<RuleName> result = ruleNameServiceImpl.findById(1L);

        // Then
        assertTrue(result.isPresent());
    }


    @Test
    public void updateRuleName_whenRuleNameNotExists_thenThrowsException() {
        // Given
        Long id = 1L;
        RuleName updatedRuleName = new RuleName();
        updatedRuleName.setName("updatedName");

        when(ruleNameRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(RuntimeException.class, () -> ruleNameServiceImpl.updateRuleName(id, updatedRuleName));
        verify(ruleNameRepository, times(1)).findById(id);
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
