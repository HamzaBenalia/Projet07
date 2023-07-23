package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.CurvePoint;

import java.util.List;

public interface CurvePointService {


    void saveCurvePoint(CurvePoint curvePoint);

    List<CurvePoint> listAll();


    CurvePoint getCurvePoint(Long id);

    void deleteCurvePoint(Long id);
}
