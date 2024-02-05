package com.challenge.decrypto.application.usecase;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.challenge.decrypto.application.port.in.PutModifyMarketPort;
import com.challenge.decrypto.application.port.out.CountryDataBase;
import com.challenge.decrypto.application.port.out.MarketDataBase;
import com.challenge.decrypto.domain.CountryDomain;
import com.challenge.decrypto.domain.MarketDomain;
import com.challenge.decrypto.domain.CountryEntity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UpdateModifyMarketUseCaseTest {
    @Mock
    private CountryDataBase countryDataBase;
    @Mock
    private MarketDataBase marketDataBase;
    @InjectMocks
    private UpdateModifyMarketUseCase updateModifyMarketUseCase;
    @Test
    void updatesMarketSuccessfully() {
        String countryName = "Argentina";
        String marketCode = "AAA";
        String newDescription = "Super A";
        PutModifyMarketPort.Command command = PutModifyMarketPort.Command.builder().code(marketCode).description(newDescription).country(countryName).build();

        CountryDomain countryDomain = CountryDomain.builder().name("Argentina").build();
        MarketDomain marketDomain = MarketDomain.builder().id(1L).code("MMM").description("Market Mundial").country(countryDomain).build();

        when(countryDataBase.getInformationCountry(any(CountryEntity.class))).thenReturn(countryDomain);
        when(marketDataBase.getMarketInformation(anyString())).thenReturn(marketDomain);

        updateModifyMarketUseCase.updatesMarket(command);

        verify(countryDataBase).getInformationCountry(any(CountryEntity.class));
        verify(marketDataBase).getMarketInformation(marketCode);
        verify(marketDataBase).updateMarket(any(MarketDomain.class), any(CountryDomain.class));
        assertEquals(newDescription, marketDomain.getDescription());
    }
}

