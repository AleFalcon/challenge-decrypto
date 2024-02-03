package com.challenge.decrypto.application.port.out;

import com.challenge.decrypto.domain.CountryDomain;
import com.challenge.decrypto.domain.MarketDomain;
import com.challenge.decrypto.domain.MarketEntity;

public interface MarketDataBase {
    MarketDomain getMarketInformation(String marketCode);
    void createMarket(MarketEntity marketEntity);
    void updateMarket(MarketDomain marketDomain, CountryDomain countryDomain);
    void deleteMarket(MarketDomain marketDomain);
}
