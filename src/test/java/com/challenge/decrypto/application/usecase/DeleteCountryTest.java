package com.challenge.decrypto.application.usecase;

import com.challenge.decrypto.application.port.out.ComitenteDataBase;
import com.challenge.decrypto.application.port.out.CountryDataBase;
import com.challenge.decrypto.application.port.out.MarketDataBase;
import com.challenge.decrypto.domain.ComitenteDomain;
import com.challenge.decrypto.domain.ComitenteEntity;
import com.challenge.decrypto.domain.CountryDomain;
import com.challenge.decrypto.domain.CountryEntity;
import com.challenge.decrypto.domain.MarketDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

class DeleteCountryTest {
    @Mock
    private CountryDataBase countryDataBase;
    @Mock
    private MarketDataBase marketDataBase;
    @Mock
    private ComitenteDataBase comitenteDataBase;
    @InjectMocks
    private DeleteCountry deleteCountry;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void deleteCountrySuccessfully() {
        String countryName = "TestCountry";
        ComitenteDomain comitente = ComitenteDomain.builder().description("Comitente 1").markets(new HashSet<>(Collections.singletonList(MarketDomain.builder().id(1L).code("MMM").description("Market Mundial").build()))).build();
        CountryDomain countryDomain = CountryDomain.builder().name(countryName).build();
        MarketDomain marketDomain = MarketDomain.builder().id(1L).code("MMM").description("Market Mundial").country(countryDomain).build();
        marketDomain.setComitentes(new HashSet<>(Collections.singletonList(comitente)));
        countryDomain.setMarkets(new HashSet<>(Collections.singletonList(marketDomain)));

        when(comitenteDataBase.getComitenteInformation(any(ComitenteEntity.class))).thenReturn(comitente);
        when(marketDataBase.getMarketInformation(marketDomain.getCode())).thenReturn(marketDomain);
        when(countryDataBase.getInformationCountry(any(CountryEntity.class))).thenReturn(countryDomain);

        deleteCountry.deleteCountry(countryName);

        verify(countryDataBase).getInformationCountry(any(CountryEntity.class));
        verify(marketDataBase, times(countryDomain.getMarkets().size())).getMarketInformation(anyString());
        verify(countryDataBase).deleteCountry(countryDomain);
    }
}

