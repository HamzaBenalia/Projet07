package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.Trade;
import com.openclassrooms.poseidon.repositories.TradeRepository;
import com.openclassrooms.poseidon.services.Impl.TradeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {


    @InjectMocks
    private TradeServiceImpl tradeServiceImpl;

    @Mock
    private TradeRepository tradeRepository;


    @Captor
    private ArgumentCaptor<Trade> TradeArgumentCaptor;


    @Test
    public void testSaveTrade() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Trade trade = new Trade(
                1234567891255552388L, "Hamza", "Admin", 20.0, 40.0, 50.0, 60.0, timestamp, "secured", "high", "ben", "high", "HarryPotter",
                "Creation1", timestamp, "Revision1", timestamp, "Deal1", "DealType1", "SourceListId1", "Side1");

        tradeServiceImpl.saveTrade(trade);

        verify(tradeRepository).save(TradeArgumentCaptor.capture());
        Trade capturedTrade = TradeArgumentCaptor.getValue();
        assertThat(capturedTrade).isEqualTo(trade);
    }


    @Test
    public void testListAllTrade() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        List<Trade> expectedTrade = new ArrayList<>();
        expectedTrade.add(new Trade(
                1234567891255552388L, "Hamza", "Admin", 20.0, 40.0, 50.0, 60.0, timestamp, "secured", "high", "ben", "high", "HarryPotter",
                "Creation1", timestamp, "Revision1", timestamp, "Deal1", "DealType1", "SourceListId1", "Side1"));

        expectedTrade.add(new Trade(
                1234567891255552388L, "Eric", "User", 20.0, 40.0, 50.0, 60.0, timestamp, "secured", "high", "ben", "high", "HarryPotter",
                "Creation2", timestamp, "Revision2", timestamp, "Deal2", "DealType2", "SourceListId2", "Side2"));


        when(tradeRepository.findAll()).thenReturn(expectedTrade);

        // Act
        List<Trade> actualTrade = tradeServiceImpl.listAll();

        // Assert
        verify(tradeRepository).findAll();
        assertThat(actualTrade).isEqualTo(expectedTrade);
    }


    @Test
    public void updateTradeForm_whenTradeExists_returnsTrade() {
        // Given
        Long id = 1L;
        Trade trade = new Trade();
        trade.setTradeId(id);
        when(tradeRepository.findById(id)).thenReturn(Optional.of(trade));

        // When
        Trade result = tradeServiceImpl.updateTradeForm(id);

        // Then
        assertEquals(trade, result);
    }


    @Test
    public void findById_whenTradeExists_returnsTrade() {
        // Given
        Trade trade = new Trade();
        trade.setAccount("Account");
        when(tradeRepository.findById(anyLong())).thenReturn(Optional.of(trade));

        // When
        Optional<Trade> result = tradeServiceImpl.findById(1L);

        // Then
        assertTrue(result.isPresent());
    }


    @Test
    public void updateTrade_whenTradeExists_thenUpdatesAndReturnsTrade() {
        // Given
        Long id = 1L;
        Trade existingTrade = new Trade();
        existingTrade.setAccount("existingAccount");
        Trade updatedTrade = new Trade();
        updatedTrade.setAccount("updatedAccount");

        when(tradeRepository.findById(id)).thenReturn(Optional.of(existingTrade));
        when(tradeRepository.save(any(Trade.class))).thenReturn(updatedTrade);

        // When
        Trade result = tradeServiceImpl.updateTrade(id, updatedTrade);

        // Then
        assertEquals(updatedTrade.getAccount(), result.getAccount());
        verify(tradeRepository, times(1)).findById(id);
        verify(tradeRepository, times(1)).save(any(Trade.class));
    }

    @Test
    public void updateTrade_whenTradeNotExists_thenThrowsException() {
        // Given
        Long id = 1L;
        Trade updatedTrade = new Trade();
        updatedTrade.setAccount("updatedAccount");

        when(tradeRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(RuntimeException.class, () -> tradeServiceImpl.updateTrade(id, updatedTrade));
        verify(tradeRepository, times(1)).findById(id);
    }


    @Test
    public void testDelete() {
        // Arrange
        Long id = 1234567890123456789L;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Trade expectedTrade = new Trade(
                id, "Hamza", "Admin", 20.0, 40.0, 50.0, 60.0, timestamp, "secured", "high", "ben", "high", "HarryPotter",
                "Creation1", timestamp, "Revision1", timestamp, "Deal1", "DealType1", "SourceListId1", "Side1");

        // Act
        tradeServiceImpl.deleteTrade(expectedTrade.getTradeId());

        // Assert
        verify(tradeRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteShouldReturnZeroTimes() {
        // Arrange
        Long id = 1234567890123456789L;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Trade expectedTrade = new Trade(
                1234567891255552388L, "Hamza", "Admin", 20.0, 40.0, 50.0, 60.0, timestamp, "secured", "high", "ben", "high", "HarryPotter",
                "Creation1", timestamp, "Revision1", timestamp, "Deal1", "DealType1", "SourceListId1", "Side1");

        // Act
        tradeServiceImpl.deleteTrade(expectedTrade.getTradeId());

        // Assert
        verify(tradeRepository, times(0)).deleteById(id);
    }
}
