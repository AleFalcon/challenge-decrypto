package com.challenge.decrypto.application.usecase;

import com.challenge.decrypto.application.port.in.DeleteMarketPort;
import com.challenge.decrypto.application.port.out.MarketDataBase;
import com.challenge.decrypto.domain.MarketDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeleteMarket implements DeleteMarketPort {
    private final MarketDataBase marketDataBase;
    public DeleteMarket(MarketDataBase marketDataBase) {
        this.marketDataBase = marketDataBase;
    }
    @Override
    @CacheEvict(value = "stats", key = "#root.method.name")
    public void deleteMarket(String name) {
        log.info(">> Ingreso al caso de uso de eliminación de mercado.");
        MarketDomain marketDomain = marketDataBase.getMarketInformation(name);
        marketDataBase.deleteMarket(marketDomain);
        log.info("<< Finalizó la ejecución del CU de eliminación del mercado.");
    }
}
