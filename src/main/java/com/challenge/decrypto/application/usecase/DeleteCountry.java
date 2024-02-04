package com.challenge.decrypto.application.usecase;

import com.challenge.decrypto.application.port.in.DeleteCountryPort;
import com.challenge.decrypto.application.port.out.ComitenteDataBase;
import com.challenge.decrypto.application.port.out.CountryDataBase;
import com.challenge.decrypto.application.port.out.MarketDataBase;
import com.challenge.decrypto.domain.ComitenteDomain;
import com.challenge.decrypto.domain.ComitenteEntity;
import com.challenge.decrypto.domain.CountryDomain;
import com.challenge.decrypto.domain.CountryEntity;
import com.challenge.decrypto.domain.MarketDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeleteCountry implements DeleteCountryPort {
    private final CountryDataBase countryDataBase;
    private final MarketDataBase marketDataBase;
    private final ComitenteDataBase comitenteDataBase;
    public DeleteCountry(CountryDataBase countryDataBase, MarketDataBase marketDataBase, ComitenteDataBase comitenteDataBase) {
        this.countryDataBase = countryDataBase;
        this.marketDataBase = marketDataBase;
        this.comitenteDataBase = comitenteDataBase;
    }
    @Override
    @CacheEvict(value = "stats", key = "#root.method.name")
    public void deleteCountry(String name) {
        log.info(">> Ingreso al caso de uso de eliminación de país.");
        CountryDomain countryDomain = countryDataBase.getInformationCountry(new CountryEntity(name));
        for(MarketDomain market : countryDomain.getMarkets()){
            MarketDomain marketFound = marketDataBase.getMarketInformation(market.getCode());
            for(ComitenteDomain comitenteDomain : marketFound.getComitentes()) {
                comitenteDomain = comitenteDataBase.getComitenteInformation(ComitenteEntity.fromDomain(comitenteDomain));
                comitenteDomain.getMarkets().removeIf(element -> element.getCode().equals(marketFound.getCode()));
                comitenteDataBase.saveComitente(comitenteDomain);
            }
            marketDataBase.deleteMarket(marketFound);
        }
        countryDataBase.deleteCountry(countryDomain);
        log.info("<< Finalizó la ejecución del CU de eliminación del país.");
    }
}
