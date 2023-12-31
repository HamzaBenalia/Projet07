package com.openclassrooms.poseidon.services;


import com.openclassrooms.poseidon.domain.CurvePoint;
import com.openclassrooms.poseidon.domain.Rating;
import com.openclassrooms.poseidon.repositories.CurvePointRepository;
import com.openclassrooms.poseidon.repositories.RatingRepository;
import com.openclassrooms.poseidon.services.Impl.CurvePointServiceImpl;
import com.openclassrooms.poseidon.services.Impl.RatingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {



    @InjectMocks
    private CurvePointServiceImpl curvePointServiceImpl;

    @Mock
    private CurvePointRepository curvePointRepository;


    @Captor
    private ArgumentCaptor<CurvePoint> CurvePointArgumentCaptor;


    @Test
    public void testSaveCurvePoint() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        CurvePoint curvePoint = new CurvePoint(123456789123L, 123456987456L,timestamp,50.0,20.0,timestamp);

        curvePointServiceImpl.saveCurvePoint(curvePoint);

        verify(curvePointRepository).save(CurvePointArgumentCaptor.capture());
        CurvePoint capturedCurvePoint = CurvePointArgumentCaptor.getValue();
        assertThat(capturedCurvePoint).isEqualTo(curvePoint);
    }


    @Test
    public void testListAllCurvePoint() {
        // Arrange
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        List<CurvePoint> expectedCurvePoint = new ArrayList<>();
        expectedCurvePoint.add(new CurvePoint(123456789123L, 123456987456L,timestamp,50.0,20.0,timestamp));
        expectedCurvePoint.add(new CurvePoint(987654321123L, 987654456321L,timestamp,20.0,50.0,timestamp));

        when(curvePointRepository.findAll()).thenReturn(expectedCurvePoint);

        // Act
        List<CurvePoint> actualRating = curvePointServiceImpl.listAll();

        // Assert
        verify(curvePointRepository).findAll();
        assertThat(actualRating).isEqualTo(expectedCurvePoint);
    }



    @Test
    public void updateCurvePoint_whenCurvePointExists_thenUpdatesAndReturnsCurvePoint() {
        // Given
        Long id = 1L;
        CurvePoint existingCurvePoint = new CurvePoint();
        existingCurvePoint.setCurveId(1L);
        CurvePoint updatedCurvePoint = new CurvePoint();
        updatedCurvePoint.setCurveId(2L);

        when(curvePointRepository.findById(id)).thenReturn(Optional.of(existingCurvePoint));
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(updatedCurvePoint);

        // When
        CurvePoint result = curvePointServiceImpl.updateCurvePoint(id, updatedCurvePoint);

        // Then
        assertEquals(updatedCurvePoint.getCurveId(), result.getCurveId());
        verify(curvePointRepository, times(1)).findById(id);
        verify(curvePointRepository, times(1)).save(any(CurvePoint.class));
    }

    @Test
    public void listAll_whenCurvePointsExist_returnsAllCurvePoints() {
        // Given
        CurvePoint curvePoint1 = new CurvePoint();
        curvePoint1.setCurveId(1L);
        CurvePoint curvePoint2 = new CurvePoint();
        curvePoint2.setCurveId(2L);
        when(curvePointRepository.findAll()).thenReturn(Arrays.asList(curvePoint1, curvePoint2));

        // When
        List<CurvePoint> result = curvePointServiceImpl.listAll();

        // Then
        assertEquals(2, result.size());
    }


    @Test
    public void updateCurvePoint_whenCurvePointNotExists_thenThrowsException() {
        // Given
        Long id = 1L;
        CurvePoint updatedCurvePoint = new CurvePoint();
        updatedCurvePoint.setCurveId(2L);

        when(curvePointRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(RuntimeException.class, () -> curvePointServiceImpl.updateCurvePoint(id, updatedCurvePoint));
        verify(curvePointRepository, times(1)).findById(id);
    }


    @Test
    public void testGetRuleName() {
        // Arrange
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Long id = 1234567890123456789L;
        CurvePoint expectedCurvePoint = new CurvePoint(id,123456987456L,timestamp,50.0,20.0,timestamp);
        Optional<CurvePoint> expectedOptional = Optional.of(expectedCurvePoint);

        when(curvePointRepository.findById(id)).thenReturn(expectedOptional);

        // Act
        CurvePoint actualCurvePoint = curvePointServiceImpl.updateCurvePoint(id);

        // Assert
        verify(curvePointRepository).findById(id);
        assertThat(actualCurvePoint).isEqualTo(expectedCurvePoint);
    }

    @Test
    public void testDeleteCurvePoint() {
        // Arrange
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Long id = 1234567890123456789L;
        CurvePoint curvePointToDelete = new CurvePoint(id, 123456987456L,timestamp,50.0,20.0,timestamp);

        // Act
        curvePointServiceImpl.deleteCurvePoint(curvePointToDelete.getId());

        // Assert
        verify(curvePointRepository, times(1)).deleteById(id);
    }

}
