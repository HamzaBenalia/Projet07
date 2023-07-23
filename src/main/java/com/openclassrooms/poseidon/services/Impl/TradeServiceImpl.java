package com.openclassrooms.poseidon.services.Impl;

import com.openclassrooms.poseidon.domain.Trade;
import com.openclassrooms.poseidon.repositories.TradeRepository;
import com.openclassrooms.poseidon.services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeRepository tradeRepository;


    @Override
    public void saveTrade(Trade trade) {
        tradeRepository.save(trade);
    }

    @Override
    public List<Trade> listAll() {
        return tradeRepository.findAll();
    }

    @Override
    public Trade getTrade(Long id) {
        return tradeRepository.findById(id).get();
    }

    @Override
    public void deleteTrade(Long id) {
        tradeRepository.deleteById(id);
    }
}
