package com.openclassrooms.poseidon.services.Impl;
import com.openclassrooms.poseidon.domain.BidList;
import com.openclassrooms.poseidon.repositories.BidListRepository;
import com.openclassrooms.poseidon.services.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BidListServiceImpl implements BidListService {

    @Autowired
    private BidListRepository bidListRepository;


    @Override
    public void saveBidList(BidList bidList) {
        bidListRepository.save(bidList);
    }

    @Override
    public Optional<BidList> findById(Long id) {
        return bidListRepository.findById(id);
    }

    @Override
    public List<BidList> listAll() {
        return bidListRepository.findAll();
    }

    @Override
    public BidList updateBidList(Long id) {
        return bidListRepository.findById(id).get();
    }

    @Override
    public BidList newBidList(Long id, BidList updatedBidList) {
        Optional<BidList> existingBidListOpt = bidListRepository.findById(id);
        if (existingBidListOpt.isPresent()) {
            BidList existingBidList = existingBidListOpt.get();
            existingBidList.setAccount(updatedBidList.getAccount());
            existingBidList.setType(updatedBidList.getType());
            existingBidList.setBidQuantity(updatedBidList.getBidQuantity());
            return bidListRepository.save(existingBidList);
        } else {
            throw new RuntimeException("BidList not found with id " + id);
        }
    }

    @Override
    public void deleteBidList(Long id) {
        bidListRepository.deleteById(id);
    }
}

