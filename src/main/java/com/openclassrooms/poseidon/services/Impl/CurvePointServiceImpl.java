package com.openclassrooms.poseidon.services.Impl;
import com.openclassrooms.poseidon.domain.CurvePoint;
import com.openclassrooms.poseidon.repositories.CurvePointRepository;
import com.openclassrooms.poseidon.services.CurvePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CurvePointServiceImpl implements CurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Override
    public Optional<CurvePoint> findById(Long id) {
        return curvePointRepository.findById(id);
    }


    @Override
    public void saveCurvePoint(CurvePoint curvePoint) {
        curvePointRepository.save(curvePoint);
    }

    @Override
    public List<CurvePoint> listAll() {
        return curvePointRepository.findAll();
    }

    @Override
    public CurvePoint updateCurvePoint(Long id, CurvePoint updatedCurvePoint) {
        Optional<CurvePoint> existingCurvePointOpt = curvePointRepository.findById(id);
        if (existingCurvePointOpt.isPresent()) {
            CurvePoint existingCurvePoint = existingCurvePointOpt.get();
            existingCurvePoint.setCurveId(updatedCurvePoint.getCurveId());
            existingCurvePoint.setValue(updatedCurvePoint.getValue());
            existingCurvePoint.setTerm(updatedCurvePoint.getTerm());
            return curvePointRepository.save(existingCurvePoint);
        } else {
            throw new RuntimeException("CurvePoint not found with id " + id);
        }
    }


    @Override
    public CurvePoint updateCurvePoint(Long id) {
        return curvePointRepository.findById(id).get();
    }

    @Override
    public void deleteCurvePoint(Long id) {
        curvePointRepository.deleteById(id);
    }
}
