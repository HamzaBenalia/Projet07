package com.openclassrooms.poseidon.services.Impl;

import com.openclassrooms.poseidon.domain.CurvePoint;
import com.openclassrooms.poseidon.repositories.CurvePointRepository;
import com.openclassrooms.poseidon.services.CurvePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurvePointServiceImpl implements CurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;


    @Override
    public void saveCurvePoint(CurvePoint curvePoint) {
        curvePointRepository.save(curvePoint);
    }

    @Override
    public List<CurvePoint> listAll() {
        return curvePointRepository.findAll();
    }

    @Override
    public CurvePoint getCurvePoint(Long id) {
        return curvePointRepository.findById(id).get();
    }

    @Override
    public void deleteCurvePoint(Long id) {
        curvePointRepository.deleteById(id);
    }
}
