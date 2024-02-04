package com.challenge.decrypto.application.usecase;

import com.challenge.decrypto.application.port.in.PostCreateMarketPort;
import com.challenge.decrypto.application.port.out.CountryDataBase;
import com.challenge.decrypto.application.port.out.MarketDataBase;
import com.challenge.decrypto.domain.CountryDomain;
import com.challenge.decrypto.domain.CountryEntity;
import com.challenge.decrypto.domain.MarketEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CreateMarketUseCase implements PostCreateMarketPort {
    private final MarketDataBase marketDataBase;
    private final CountryDataBase countryDataBase;
    public CreateMarketUseCase(MarketDataBase marketDataBase, CountryDataBase countryDataBase) {
        this.marketDataBase = marketDataBase;
        this.countryDataBase = countryDataBase;
    }
    @Override
    @CacheEvict(value = "stats", key = "#root.method.name")
    public void createMarket(Command command) {
        log.info(">> Ingreso al caso de uso de creación del mercado.");
        CountryDomain countryDomain = countryDataBase.getInformationCountry(
                new CountryEntity(command.getCountryName()));
        MarketEntity marketEntity = new MarketEntity(command.getCode(), command.getDescription(),
                CountryEntity.fromDomain(countryDomain));
        marketDataBase.createMarket(marketEntity);
        log.info("<< Finalización de creación del mercado.");
    }
}
