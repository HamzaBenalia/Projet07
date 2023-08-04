package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.CurvePoint;

import java.util.List;
import java.util.Optional;

public interface CurvePointService {


    void saveCurvePoint(CurvePoint curvePoint);

    List<CurvePoint> listAll();

    CurvePoint updateCurvePoint(Long id, CurvePoint updatedCurvePoint);

    Optional<CurvePoint> findById(Long id);


    CurvePoint updateCurvePoint(Long id);

    void deleteCurvePoint(Long id);
}
