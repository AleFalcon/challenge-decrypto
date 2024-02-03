package com.challenge.decrypto.adapter.jdbc;

import com.challenge.decrypto.application.exception.ElementExisteException;
import com.challenge.decrypto.application.exception.NotFoundException;
import com.challenge.decrypto.application.port.out.MarketDataBase;
import com.challenge.decrypto.application.port.out.MarketRepository;
import com.challenge.decrypto.domain.CountryDomain;
import com.challenge.decrypto.domain.CountryEntity;
import com.challenge.decrypto.domain.MarketDomain;
import com.challenge.decrypto.domain.MarketEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class MarketJdbcAdapter implements MarketDataBase {
    private final MarketRepository marketRepository;
    public MarketJdbcAdapter(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }
    @Transactional(readOnly = true)
    @Override
    public MarketDomain getMarketInformation(String marketCode) {
        log.info(">> Inicio de obtención de datos del mercado");
        MarketEntity marketEntity = marketRepository.findByCode(marketCode)
                .orElseThrow(() -> {
                    log.warn("<< No se encontró el mercado");
                    throw new NotFoundException("Market not found");
                });
        return MarketDomain.toDomain(marketEntity);
    }
    @Override
    public void createMarket(MarketEntity marketEntity) {
        try {
            log.info(">> Inicio de guardado de mercado");
            marketRepository.save(marketEntity);
        } catch (DataIntegrityViolationException e) {
            log.info("<< El mercado ya existe");
            throw new ElementExisteException("The country already exists");
        }
    }
    @Override
    public void updateMarket(MarketDomain marketDomain, CountryDomain countryDomain) {
        MarketEntity marketEntity = MarketDomain.toModel(marketDomain);
        marketEntity.setCountry(CountryEntity.fromDomain(countryDomain));
        log.info(">> Inicio de guardado de mercado");
        marketRepository.save(marketEntity);
    }
    @Override
    public void deleteMarket(MarketDomain marketDomain) {
        log.info("<< Inicio de eliminación del mercado");
        marketRepository.delete(MarketDomain.toModel(marketDomain));
        log.info(">> Finalizó la eliminación del mercado");
    }
}
