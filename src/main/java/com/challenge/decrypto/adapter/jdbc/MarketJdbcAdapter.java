package com.challenge.decrypto.adapter.jdbc;

import com.challenge.decrypto.application.exception.ElementExisteException;
import com.challenge.decrypto.application.exception.NotFoundException;
import com.challenge.decrypto.application.port.out.MarketDataBase;
import com.challenge.decrypto.application.port.out.MarketRepository;
import com.challenge.decrypto.domain.CountryDomain;
import com.challenge.decrypto.domain.CountryEntity;
import com.challenge.decrypto.domain.MarketDomain;
import com.challenge.decrypto.domain.MarketEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MarketJdbcAdapter implements MarketDataBase {
    private final MarketRepository marketRepository;
    public MarketJdbcAdapter(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }
    @Transactional(readOnly = true)
    @Override
    public MarketDomain getMarketInformation(String marketCode) {
        MarketEntity marketEntity = marketRepository.findByCode(marketCode)
                .orElseThrow(() -> new NotFoundException("Market not found"));
        return MarketDomain.toDomain(marketEntity);
    }
    @Override
    public void createMarket(MarketEntity marketEntity) {
        try {
            marketRepository.save(marketEntity);
        } catch (DataIntegrityViolationException e) {
            throw new ElementExisteException("The country already exists");
        }
    }
    @Override
    public void updateMarket(MarketDomain marketDomain, CountryDomain countryDomain) {
        MarketEntity marketEntity = MarketDomain.toModel(marketDomain);
        marketEntity.setCountry(CountryEntity.fromDomain(countryDomain));
        marketRepository.save(marketEntity);
    }
    @Override
    public void deleteMarket(MarketDomain marketDomain) {
        marketRepository.delete(MarketDomain.toModel(marketDomain));
    }
}
