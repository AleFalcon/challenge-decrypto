package com.challenge.decrypto.application.usecase;

import com.challenge.decrypto.application.port.in.PutModifyMarketPort;
import com.challenge.decrypto.application.port.out.CountryDataBase;
import com.challenge.decrypto.application.port.out.MarketDataBase;
import com.challenge.decrypto.domain.CountryDomain;
import com.challenge.decrypto.domain.CountryEntity;
import com.challenge.decrypto.domain.MarketDomain;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
public class UpdateModifyMarketUseCase implements PutModifyMarketPort {
    private final CountryDataBase countryDataBase;
    private final MarketDataBase marketDataBase;
    public UpdateModifyMarketUseCase(CountryDataBase countryDataBase, MarketDataBase marketDataBase) {
        this.countryDataBase = countryDataBase;
        this.marketDataBase = marketDataBase;
    }
    @Override
    @CacheEvict(value = "status", key = "#root.method.name")
    public void updatesMarket(Command command) {
        CountryDomain countryDomain = countryDataBase.getInformationCountry(new CountryEntity(command.getCountry()));
        MarketDomain marketDomain = marketDataBase.getMarketInformation(command.getCode());
        marketDomain.setDescription(command.getDescription() == null ? marketDomain.getDescription() : command.getDescription());
        marketDataBase.updateMarket(marketDomain, countryDomain);
    }
}
