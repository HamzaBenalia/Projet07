package com.openclassrooms.poseidon.services;
import com.openclassrooms.poseidon.domain.BidList;
import com.openclassrooms.poseidon.domain.CurvePoint;
import com.openclassrooms.poseidon.repositories.BidListRepository;
import com.openclassrooms.poseidon.services.Impl.BidListServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTest {


    @InjectMocks
    private BidListServiceImpl bidListServiceImpl;

    @Mock
    private BidListRepository bidListRepository;


    @Captor
    private ArgumentCaptor<BidList> bidListArgumentCaptor;


    @Test
    public void testSaveBidList() {
        LocalDateTime now = LocalDateTime.now();

        BidList bidList = new BidList(
                1234567891255552388L, "Account1", "Type1", 100.0, 200.0, 50.0,
                60.0, "Benchmark1", now, "Commentary1", "Security1", "Status1",
                "Trader1", "Book1", "Creation1", now,
                "Revision1", now, "Deal1", "DealType1", "SourceListId1", "Side1"
        );

        bidListServiceImpl.saveBidList(bidList);

        verify(bidListRepository).save(bidListArgumentCaptor.capture());
        BidList capturedBidList = bidListArgumentCaptor.getValue();
        assertThat(capturedBidList).isEqualTo(bidList);
    }


    @Test
    public void testListAllCurvePoint() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        List<BidList> expectedBidList = new ArrayList<>();
        expectedBidList.add(new BidList(
                1234567891255552388L, "Account1", "Type1", 100.0, 200.0, 50.0,
                60.0, "Benchmark1", now, "Commentary1", "Security1", "Status1",
                "Trader1", "Book1", "Creation1", now,
                "Revision1", now, "Deal1", "DealType1", "SourceListId1", "Side1"
        ));

        expectedBidList.add(new BidList(
                123456789125557742L, "Account2", "Type2", 100.0, 300.0, 50.0,
                60.0, "Benchmark2", now, "Commentary2", "Security2", "Status2",
                "Trader2", "Book2", "Creation2", now,
                "Revision1", now, "Deal1", "DealType1", "SourceListId1", "Side1"
        ));

        when(bidListRepository.findAll()).thenReturn(expectedBidList);

        // Act
        List<BidList> actualBidList = bidListServiceImpl.listAll();

        // Assert
        verify(bidListRepository).findAll();
        assertThat(actualBidList).isEqualTo(expectedBidList);
    }


    @Test
    public void testGetBidList() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        Long id = 1234567890123456789L;
        BidList expectedBidList = new BidList(id, "Account2", "Type2", 100.0, 300.0, 50.0,
                60.0, "Benchmark2", now, "Commentary2", "Security2", "Status2",
                "Trader2", "Book2", "Creation2", now,
                "Revision1", now, "Deal1", "DealType1", "SourceListId1", "Side1"
        );
        Optional<BidList> expectedOptional = Optional.of(expectedBidList);
        when(bidListRepository.findById(id)).thenReturn(expectedOptional);

        // Act
        BidList actualBidList = bidListServiceImpl.updateBidList(id);

        // Assert
        verify(bidListRepository).findById(id);
        assertThat(actualBidList).isEqualTo(expectedBidList);
    }

    @Test
    public void newBidList_whenBidListExists_thenUpdatesAndReturnsBidList() {
        // Given
        Long id = 1L;
        BidList existingBidList = new BidList();
        existingBidList.setAccount("existingAccount");
        BidList updatedBidList = new BidList();
        updatedBidList.setAccount("updatedAccount");

        when(bidListRepository.findById(id)).thenReturn(Optional.of(existingBidList));
        when(bidListRepository.save(any(BidList.class))).thenReturn(updatedBidList);

        // When
        BidList result = bidListServiceImpl.newBidList(id, updatedBidList);

        // Then
        assertEquals(updatedBidList.getAccount(), result.getAccount());
        verify(bidListRepository, times(1)).findById(id);
        verify(bidListRepository, times(1)).save(any(BidList.class));
    }

    @Test
    public void listAll_whenBidListsExist_returnsAllBidLists() {
        // Given
        BidList bidList1 = new BidList();
        bidList1.setAccount("Account 1");
        BidList bidList2 = new BidList();
        bidList2.setAccount("Account 2");
        when(bidListRepository.findAll()).thenReturn(Arrays.asList(bidList1, bidList2));

        // When
        List<BidList> result = bidListServiceImpl.listAll();

        // Then
        assertEquals(2, result.size());
    }


    @Test
    public void newBidList_whenBidListNotExists_thenThrowsException() {
        // Given
        Long id = 1L;
        BidList updatedBidList = new BidList();
        updatedBidList.setAccount("updatedAccount");

        when(bidListRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(RuntimeException.class, () -> bidListServiceImpl.newBidList(id, updatedBidList));
        verify(bidListRepository, times(1)).findById(id);
    }

    @Test
    public void testDeleteCurvePoint() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        Long id = 1234567890123456789L;
        BidList expectedBidList = new BidList(id, "Account2", "Type2", 100.0, 300.0, 50.0,
                60.0, "Benchmark2", now, "Commentary2", "Security2", "Status2",
                "Trader2", "Book2", "Creation2", now,
                "Revision1", now, "Deal1", "DealType1", "SourceListId1", "Side1"
        );

        // Act
        bidListServiceImpl.deleteBidList(expectedBidList.getBidListId());

        // Assert
        verify(bidListRepository, times(1)).deleteById(id);
    }
}
