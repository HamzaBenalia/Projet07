package com.openclassrooms.poseidon.services.Impl;
import com.openclassrooms.poseidon.domain.Trade;
import com.openclassrooms.poseidon.repositories.TradeRepository;
import com.openclassrooms.poseidon.services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeRepository tradeRepository;


    @Override
    public void saveTrade(Trade trade) {
        tradeRepository.save(trade);
    }

    @Override
    public Optional<Trade> findById(Long id) {
        return tradeRepository.findById(id);
    }

    @Override
    public List<Trade> listAll() {
        return tradeRepository.findAll();
    }

    @Override
    public Trade updateTrade(Long id, Trade updatedTrade) {
        Optional<Trade> existingTradeOpt = tradeRepository.findById(id);
        if (existingTradeOpt.isPresent()) {
            Trade existingTrade = existingTradeOpt.get();
            existingTrade.setAccount(updatedTrade.getAccount());
            existingTrade.setBuyQuantity(updatedTrade.getBuyQuantity());
            existingTrade.setType(updatedTrade.getType());
            return tradeRepository.save(existingTrade);
        } else {
            throw new RuntimeException("Trade not found with id " + id);
        }
    }


    @Override
    public Trade updateTradeForm(Long id) {
        return tradeRepository.findById(id).get();
    }

    @Override
    public void deleteTrade(Long id) {
        tradeRepository.deleteById(id);
    }
}
