package com.challenge.decrypto.application.usecase;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.challenge.decrypto.application.port.in.PutModifyComitentePort;
import com.challenge.decrypto.adapter.jdbc.MarketJdbcAdapter;
import com.challenge.decrypto.application.port.out.ComitenteDataBase;
import com.challenge.decrypto.domain.ComitenteDomain;
import com.challenge.decrypto.domain.ComitenteEntity;
import com.challenge.decrypto.domain.CountryDomain;
import com.challenge.decrypto.domain.MarketDomain;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
class UpdateModifyComitenteUseCaseTest {
    @Mock
    private ComitenteDataBase comitenteDataBase;
    @Mock
    private MarketJdbcAdapter marketDataBase;
    @InjectMocks
    private UpdateModifyComitenteUseCase updateModifyComitenteUseCase;
    @Test
    void updatesMarketsForComitenteAddsMarketsSuccessfully() {
        PutModifyComitentePort.Command command = PutModifyComitentePort.Command.builder().addMarkets(List.of("MMM", "AAA")).deleteMarkets(null).build();

        ComitenteDomain comitenteDomain = ComitenteDomain.builder().description("Comitente 1").markets(new HashSet<>()).build();
        CountryDomain countryDomain = CountryDomain.builder().name("Argentina").build();
        MarketDomain marketDomain1 = MarketDomain.builder().id(1L).code("MMM").description("Market Mundial").country(countryDomain).build();
        MarketDomain marketDomain2 = MarketDomain.builder().id(1L).code("AAA").description("Market Mundial").country(countryDomain).build();


        when(comitenteDataBase.getComitenteInformation(any(ComitenteEntity.class))).thenReturn(comitenteDomain);
        when(marketDataBase.getMarketInformation("MMM")).thenReturn(marketDomain1);
        when(marketDataBase.getMarketInformation("AAA")).thenReturn(marketDomain2);

        updateModifyComitenteUseCase.updatesMarketsForComitente(command);

        verify(comitenteDataBase).getComitenteInformation(any(ComitenteEntity.class));
        verify(marketDataBase).getMarketInformation("MMM");
        verify(marketDataBase).getMarketInformation("AAA");
        assertTrue(comitenteDomain.getMarkets().contains(marketDomain1));
        assertTrue(comitenteDomain.getMarkets().contains(marketDomain2));
        verify(comitenteDataBase).saveComitente(comitenteDomain);
    }
    @Test
    void updatesMarketsForComitenteRemoveMarketsSuccessfully() {
        PutModifyComitentePort.Command command = PutModifyComitentePort.Command.builder().addMarkets(null).deleteMarkets(List.of("MMM", "AAA")).build();

        ComitenteDomain comitenteDomain = ComitenteDomain.builder().description("Comitente 1").markets(new HashSet<>()).build();
        CountryDomain countryDomain = CountryDomain.builder().name("Argentina").build();
        MarketDomain marketDomain1 = MarketDomain.builder().id(1L).code("MMM").description("Market Mundial").country(countryDomain).build();
        MarketDomain marketDomain2 = MarketDomain.builder().id(1L).code("AAA").description("Market Mundial").country(countryDomain).build();
        comitenteDomain.setMarkets(new HashSet<>(Set.of(marketDomain1, marketDomain2)));

        when(comitenteDataBase.getComitenteInformation(any(ComitenteEntity.class))).thenReturn(comitenteDomain);
        when(marketDataBase.getMarketInformation("MMM")).thenReturn(marketDomain1);
        when(marketDataBase.getMarketInformation("AAA")).thenReturn(marketDomain2);

        updateModifyComitenteUseCase.updatesMarketsForComitente(command);

        verify(comitenteDataBase).getComitenteInformation(any(ComitenteEntity.class));
        verify(marketDataBase).getMarketInformation("MMM");
        verify(marketDataBase).getMarketInformation("AAA");
        assertFalse(comitenteDomain.getMarkets().contains(marketDomain1));
        assertFalse(comitenteDomain.getMarkets().contains(marketDomain2));
        verify(comitenteDataBase).saveComitente(comitenteDomain);
    }
}

