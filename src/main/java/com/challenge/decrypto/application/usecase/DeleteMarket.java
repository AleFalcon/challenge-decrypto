package com.challenge.decrypto.application.usecase;

import com.challenge.decrypto.application.port.in.DeleteMarketPort;
import com.challenge.decrypto.application.port.out.MarketDataBase;
import com.challenge.decrypto.domain.MarketDomain;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
public class DeleteMarket implements DeleteMarketPort {
    private final MarketDataBase marketDataBase;
    public DeleteMarket(MarketDataBase marketDataBase) {
        this.marketDataBase = marketDataBase;
    }
    @Override
    @CacheEvict(value = "status", key = "#root.method.name")
    public void deleteMarket(String name) {
        MarketDomain marketDomain = marketDataBase.getMarketInformation(name);
        marketDataBase.deleteMarket(marketDomain);
    }
}
