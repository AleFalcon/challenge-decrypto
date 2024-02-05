package com.challenge.decrypto.adapter.jdbc;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.challenge.decrypto.application.exception.ElementExisteException;
import com.challenge.decrypto.application.exception.NotFoundException;
import com.challenge.decrypto.application.port.out.MarketRepository;
import com.challenge.decrypto.domain.CountryDomain;
import com.challenge.decrypto.domain.CountryEntity;
import com.challenge.decrypto.domain.MarketDomain;
import com.challenge.decrypto.domain.MarketEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
class MarketJdbcAdapterTest {
    @Mock
    private MarketRepository marketRepository;
    @InjectMocks
    private MarketJdbcAdapter marketJdbcAdapter;
    @Test
    void getMarketInformationWhenMarketExists() {
        CountryEntity argentina = new CountryEntity("Argentina");
        MarketEntity mockMarketEntity = new MarketEntity("MAE", "Mercado Argentino de Valores", argentina);
        when(marketRepository.findByCode("MAE")).thenReturn(Optional.of(mockMarketEntity));

        MarketDomain result = marketJdbcAdapter.getMarketInformation("MAE");

        assertNotNull(result);
        verify(marketRepository).findByCode("MAE");
    }
    @Test
    void getMarketInformationThrowsNotFoundExceptionWhenMarketNotFound() {
        when(marketRepository.findByCode("UNKNOWN")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> marketJdbcAdapter.getMarketInformation("UNKNOWN"));
    }
    @Test
    void createMarketSucceeds() {
        CountryEntity argentina = new CountryEntity("Argentina");
        MarketEntity newMarket = new MarketEntity("RAE", "Mercado Argentino de Valores", argentina);

        when(marketRepository.save(any(MarketEntity.class))).thenReturn(newMarket);

        assertDoesNotThrow(() -> marketJdbcAdapter.createMarket(newMarket));
        verify(marketRepository, times(1)).save(any(MarketEntity.class));
    }
    @Test
    void createMarketThrowsExceptionWhenMarketExists() {
        CountryEntity argentina = new CountryEntity("Argentina");
        MarketEntity existingMarket = new MarketEntity("MAE", "Mercado Argentino de Valores", argentina);
        doThrow(new DataIntegrityViolationException("The country already exists")).when(marketRepository).save(any(MarketEntity.class));

        assertThrows(ElementExisteException.class, () -> marketJdbcAdapter.createMarket(existingMarket));
    }
    @Test
    void updateMarketSucceeds() {
        CountryEntity argentina = new CountryEntity("Argentina");
        MarketEntity MarketEntity = new MarketEntity("MAE", "Mercado Argentino de Valores", argentina);

        marketJdbcAdapter.updateMarket(MarketDomain.toDomain(MarketEntity), CountryDomain.toDomain(argentina));

        verify(marketRepository).save(any(MarketEntity.class));
    }
    @Test
    void deleteMarketSucceeds() {
        CountryEntity argentina = new CountryEntity("Argentina");
        MarketEntity MarketEntity = new MarketEntity("MAE", "Mercado Argentino de Valores", argentina);

        marketJdbcAdapter.deleteMarket(MarketDomain.toDomain(MarketEntity));

        verify(marketRepository).delete(any(MarketEntity.class));
    }
}
