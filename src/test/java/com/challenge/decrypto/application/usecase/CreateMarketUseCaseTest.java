package com.challenge.decrypto.application.usecase;

import com.challenge.decrypto.application.port.in.PostCreateMarketPort;
import com.challenge.decrypto.application.port.out.MarketDataBase;
import com.challenge.decrypto.application.port.out.CountryDataBase;
import com.challenge.decrypto.domain.CountryDomain;
import com.challenge.decrypto.domain.CountryEntity;
import com.challenge.decrypto.domain.MarketEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateMarketUseCaseTest {
    @Mock
    private MarketDataBase marketDataBase;
    @Mock
    private CountryDataBase countryDataBase;
    @InjectMocks
    private CreateMarketUseCase createMarketUseCase;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void createMarketSuccessfully() {
        PostCreateMarketPort.Command command = PostCreateMarketPort.Command.builder().code("MMA").description("Mercado Mundial").countryName("Argentina").build();

        when(countryDataBase.getInformationCountry(any(CountryEntity.class)))
                .thenReturn(CountryDomain.builder().id(1L).name( "Argentina").build());

        createMarketUseCase.createMarket(command);

        verify(marketDataBase).createMarket(any(MarketEntity.class));
        verify(countryDataBase).getInformationCountry(any(CountryEntity.class));
    }
}
