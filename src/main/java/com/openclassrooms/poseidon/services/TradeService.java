package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.Trade;

import java.util.List;
import java.util.Optional;

public interface TradeService {

    void saveTrade(Trade trade);

    List<Trade> listAll();

    Optional<Trade> findById(Long id);


    Trade updateTradeForm(Long id);

    Trade updateTrade(Long id, Trade updatedTrade);


    void deleteTrade(Long id);
}
