package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.Trade;

import java.util.List;

public interface TradeService {

    void saveTrade(Trade trade);


    List<Trade> listAll();


    Trade updateTrade(Long id);


    void deleteTrade(Long id);
}
