package com.challenge.decrypto.application.port.out;

import com.challenge.decrypto.domain.ComitenteDomain;
import com.challenge.decrypto.domain.MarketDomain;

public interface ComitenteMarketDataBase {
    void updateComitenteMarket(ComitenteDomain comitenteDomain, MarketDomain marketDomain);
}
