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
    public void testGet() {
        // Arrange
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Long id = 1234567890123456789L;
        Trade expectedTrade = new Trade(
                1234567891255552388L, "Hamza", "Admin", 20.0, 40.0, 50.0, 60.0, timestamp, "secured", "high", "ben", "high", "HarryPotter",
                "Creation1", timestamp, "Revision1", timestamp, "Deal1", "DealType1", "SourceListId1", "Side1");
        Optional<Trade> expectedOptional = Optional.of(expectedTrade);

        when(tradeRepository.findById(id)).thenReturn(expectedOptional);

        // Act
        Trade actualTrade = tradeServiceImpl.getTrade(id);

        // Assert
        verify(tradeRepository).findById(id);
        assertThat(actualTrade).isEqualTo(expectedTrade);
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
