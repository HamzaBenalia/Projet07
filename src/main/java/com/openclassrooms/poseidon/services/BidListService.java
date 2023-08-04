package com.openclassrooms.poseidon.services;
import com.openclassrooms.poseidon.domain.BidList;
import java.util.List;
import java.util.Optional;
public interface BidListService {


    void saveBidList(BidList bidList);


    List<BidList> listAll();

    BidList updateBidList(Long id);

    Optional<BidList> findById(Long id);

    BidList newBidList(Long id, BidList updatedBidList);


    void deleteBidList(Long id);

}
