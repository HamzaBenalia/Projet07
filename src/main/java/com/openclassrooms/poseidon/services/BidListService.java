package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.BidList;

import java.util.List;

public interface BidListService {


    void saveBidList(BidList bidList);


    List<BidList> listAll();

    BidList updateBidList(Long id);


    void deleteBidList(Long id);

}
