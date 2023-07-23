package com.openclassrooms.poseidon.services.Impl;

import com.openclassrooms.poseidon.domain.BidList;
import com.openclassrooms.poseidon.repositories.BidListRepository;
import com.openclassrooms.poseidon.services.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidListServiceImpl implements BidListService {

    @Autowired
    private BidListRepository bidListRepository;


    @Override
    public void saveBidList(BidList bidList) {
        bidListRepository.save(bidList);
    }

    @Override
    public List<BidList> listAll() {
        return bidListRepository.findAll();
    }

    @Override
    public BidList getBidList(Long id) {
        return bidListRepository.findById(id).get();
    }

    @Override
    public void deleteBidList(Long id) {
        bidListRepository.deleteById(id);
    }
}

