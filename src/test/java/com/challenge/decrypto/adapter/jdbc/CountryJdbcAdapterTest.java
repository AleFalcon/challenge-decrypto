package com.challenge.decrypto.adapter.jdbc;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.challenge.decrypto.application.exception.ElementExisteException;
import com.challenge.decrypto.application.exception.NotFoundException;
import com.challenge.decrypto.application.port.out.CountryRepository;
import com.challenge.decrypto.domain.CountryEntity;
import com.challenge.decrypto.domain.CountryDomain;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class CountryJdbcAdapterTest {
    @Mock
    private CountryRepository countryRepository;
    @InjectMocks
    private CountryJdbcAdapter countryJdbcAdapter;

    @Test
    void getAllInformationReturnsListOfCountries() {
        List<CountryEntity> countryEntities = new ArrayList<>();
        countryEntities.add(new CountryEntity("Argentina"));
        countryEntities.add(new CountryEntity( "Brasil"));
        when(countryRepository.findAllComitentesForMarketsByCountry()).thenReturn(countryEntities);

        List<CountryDomain> result = countryJdbcAdapter.getAllInformation();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(countryRepository, times(1)).findAllComitentesForMarketsByCountry();
    }
    @Test
    void getInformationCountryReturnsCountryWhenFound() {
        CountryEntity countryEntity = new CountryEntity("Argentina");
        when(countryRepository.findByName("Argentina")).thenReturn(Optional.of(countryEntity));

        CountryDomain result = countryJdbcAdapter.getInformationCountry(new CountryEntity("Argentina"));

        assertNotNull(result);
        assertEquals("Argentina", result.getName());
    }
    @Test
    void getInformationCountryThrowsNotFoundExceptionWhenCountryNotFound() {
        when(countryRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> countryJdbcAdapter.getInformationCountry(new CountryEntity("Country not found")));
    }
    @Test
    void saveCountrySavesSuccessfully() {
        CountryDomain countryDomain = CountryDomain.builder().name("Argentina").build();
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setName("Argentina");
        when(countryRepository.save(any(CountryEntity.class))).thenReturn(countryEntity);

        assertDoesNotThrow(() -> countryJdbcAdapter.saveCountry(countryDomain));

        verify(countryRepository, times(1)).save(any(CountryEntity.class));
    }
    @Test
    void saveCountryThrowsElementExisteExceptionWhenCountryExists() {
        CountryDomain countryDomain = CountryDomain.builder().name("Argentina").build();
        doThrow(new DataIntegrityViolationException("")).when(countryRepository).save(any(CountryEntity.class));

        assertThrows(ElementExisteException.class, () -> countryJdbcAdapter.saveCountry(countryDomain));
    }
    @Test
    void deleteCountrySucceeds() {
        CountryDomain countryDomain = CountryDomain.builder().name("Argentina").build();

        countryJdbcAdapter.deleteCountry(countryDomain);

        verify(countryRepository).delete(any(CountryEntity.class));
    }
}

