package com.challenge.decrypto.application.usecase;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.challenge.decrypto.application.port.out.ComitenteDataBase;
import com.challenge.decrypto.application.port.out.CountryDataBase;
import com.challenge.decrypto.domain.ComitenteDomain;
import com.challenge.decrypto.domain.CountryDomain;
import com.challenge.decrypto.domain.MarketDomain;
import com.challenge.decrypto.domain.StatsDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@SpringBootTest
class GetStatsUseCaseTest {
    @Mock
    private ComitenteDataBase comitenteDataBase;
    @Mock
    private CountryDataBase countryDataBase;
    @Mock
    private CacheManager cacheManager;
    @InjectMocks
    private GetStatsUseCase getStatsUseCase;
    @Test
    void getStatsReturnsCorrectStats() {
        CountryDomain country = CountryDomain.builder().id(1L).name("Argentina").build();
        MarketDomain marketDomain = MarketDomain.builder().id(1L).code("MMM").description("Market Mundial").country(country).build();
        ComitenteDomain comitente = ComitenteDomain.builder().description("Comitente 1").markets(new HashSet<>(Collections.singletonList(MarketDomain.builder().id(1L).code("MMM").description("Market Mundial").build()))).build();
        marketDomain.setComitentes(new HashSet<>(Collections.singletonList(comitente)));
        country.setMarkets(new HashSet<>(Collections.singletonList(marketDomain)));

        when(countryDataBase.getAllInformation()).thenReturn(Collections.singletonList(country));
        when(comitenteDataBase.getCountComitentes()).thenReturn(1);

        List<StatsDomain> result = getStatsUseCase.getStats();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(countryDataBase).getAllInformation();
        verify(comitenteDataBase).getCountComitentes();
    }
}
