package com.challenge.decrypto.application.usecase;

import com.challenge.decrypto.application.port.in.PostCreateCountryPort;
import com.challenge.decrypto.application.port.out.CountryDataBase;
import com.challenge.decrypto.domain.CountryDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class CreateCountryUseCaseTest {
    @Mock
    private CountryDataBase countryDataBase;
    @InjectMocks
    private CreateCountryUseCase createCountryUseCase;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void createCountrySuccessfully() {
        PostCreateCountryPort.Command command = PostCreateCountryPort.Command.builder().name("Argentina").build();

        createCountryUseCase.createCountry(command);

        verify(countryDataBase).saveCountry(any(CountryDomain.class));
    }
}

