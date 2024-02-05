package com.challenge.decrypto.application.usecase;

import com.challenge.decrypto.application.port.out.MarketDataBase;
import com.challenge.decrypto.domain.CountryDomain;
import com.challenge.decrypto.domain.MarketDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

class DeleteMarketTest {
    @Mock
    private MarketDataBase marketDataBase;
    @InjectMocks
    private DeleteMarket deleteMarket;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void deleteMarketSuccessfully() {
        String marketName = "TestMarket";
        CountryDomain countryDomain = CountryDomain.builder().name("Argentina").build();
        MarketDomain marketDomain = MarketDomain.builder().id(1L).code("MMM").description("Market Mundial").country(countryDomain).build();

        when(marketDataBase.getMarketInformation(marketName)).thenReturn(marketDomain);

        deleteMarket.deleteMarket(marketName);

        verify(marketDataBase).getMarketInformation(marketName);
        verify(marketDataBase).deleteMarket(marketDomain);
    }
}

