package com.challenge.decrypto.application.usecase;

import com.challenge.decrypto.application.port.in.PostCreateMarketPort;
import com.challenge.decrypto.application.port.out.CountryDataBase;
import com.challenge.decrypto.application.port.out.MarketDataBase;
import com.challenge.decrypto.domain.CountryDomain;
import com.challenge.decrypto.domain.CountryEntity;
import com.challenge.decrypto.domain.MarketEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
public class CreateMarketUseCase implements PostCreateMarketPort {
    private final MarketDataBase marketDataBase;
    private final CountryDataBase countryDataBase;
    public CreateMarketUseCase(MarketDataBase marketDataBase, CountryDataBase countryDataBase) {
        this.marketDataBase = marketDataBase;
        this.countryDataBase = countryDataBase;
    }
    @Override
    @CacheEvict(value = "status", key = "#root.method.name")
    public void createMarket(Command command) {
        CountryDomain countryDomain = countryDataBase.getInformationCountry(
                new CountryEntity(command.getCountryName()));
        MarketEntity marketEntity = new MarketEntity(command.getCode(), command.getDescription(),
                CountryEntity.fromDomain(countryDomain));
        marketDataBase.createMarket(marketEntity);
    }
}
