package com.challenge.decrypto.application.usecase;

import com.challenge.decrypto.adapter.jdbc.MarketJdbcAdapter;
import com.challenge.decrypto.application.port.in.PutModifyComitentePort;
import com.challenge.decrypto.application.port.out.ComitenteDataBase;
import com.challenge.decrypto.domain.ComitenteDomain;
import com.challenge.decrypto.domain.ComitenteEntity;
import com.challenge.decrypto.domain.MarketDomain;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
public class UpdateModifyComitenteUseCase implements PutModifyComitentePort {
    private final ComitenteDataBase comitenteDataBase;
    private final MarketJdbcAdapter marketDataBase;
    public UpdateModifyComitenteUseCase(ComitenteDataBase comitenteDataBase, MarketJdbcAdapter marketDataBase) {
        this.comitenteDataBase = comitenteDataBase;
        this.marketDataBase = marketDataBase;
    }
    @Override
    @CacheEvict(value = "status", key = "#root.method.name")
    public void updatesMarketsForComitente(Command command) {
        ComitenteDomain comitenteDomain = comitenteDataBase.getComitenteInformation(new ComitenteEntity(command.getDescription()));
        if (command.getAddMarkets() != null) {
            for (String marketName : command.getAddMarkets()) {
                MarketDomain market = marketDataBase.getMarketInformation(marketName);
                comitenteDomain.getMarkets().add(market);
            }
        }
        if (command.getDeleteMarkets()!= null) {
            for (String marketName : command.getDeleteMarkets()) {
                MarketDomain market = marketDataBase.getMarketInformation(marketName);
                comitenteDomain.getMarkets().removeIf(element -> element.getCode().equals(market.getCode()));
            }
        }
        comitenteDataBase.saveComitente(comitenteDomain);
    }
}
