package com.challenge.decrypto.application.usecase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.challenge.decrypto.application.port.in.PutModifyCountryPort;
import com.challenge.decrypto.application.port.out.CountryDataBase;
import com.challenge.decrypto.domain.CountryDomain;
import com.challenge.decrypto.domain.CountryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UpdateModifyCountryUseCaseTest {
    @Mock
    private CountryDataBase countryDataBase;
    @InjectMocks
    private UpdateModifyCountryUseCase updateModifyCountryUseCase;
    @Test
    void updatesCountrySuccessfully() {
        String oldName = "Old Name";
        String newName = "New Name";
        PutModifyCountryPort.Command command = PutModifyCountryPort.Command.builder().oldName(oldName).newName(newName).build();

        CountryDomain existingCountry = CountryDomain.builder().name(oldName).build();

        when(countryDataBase.getInformationCountry(any(CountryEntity.class))).thenReturn(existingCountry);

        updateModifyCountryUseCase.updatesCountry(command);

        verify(countryDataBase).getInformationCountry(any(CountryEntity.class));
        assertEquals(newName, existingCountry.getName());
        verify(countryDataBase).saveCountry(existingCountry);
    }
}

