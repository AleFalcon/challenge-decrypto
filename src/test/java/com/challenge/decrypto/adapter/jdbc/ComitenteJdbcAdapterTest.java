package com.challenge.decrypto.adapter.jdbc;

import com.challenge.decrypto.application.exception.NotFoundException;
import com.challenge.decrypto.application.port.out.ComitenteRepository;
import com.challenge.decrypto.domain.ComitenteDomain;
import com.challenge.decrypto.domain.ComitenteEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import com.challenge.decrypto.application.exception.ElementExisteException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComitenteJdbcAdapterTest {
    @Mock
    private ComitenteRepository comitenteRepository;
    @InjectMocks
    private ComitenteJdbcAdapter comitenteJdbcAdapter;
    @Test
    void saveComitenteSuccess() {
        ComitenteDomain comitenteDomain = ComitenteDomain.builder().description("Comitente5").build();
        ComitenteEntity comitenteEntity = ComitenteEntity.fromDomain(comitenteDomain);

        when(comitenteRepository.save(any(ComitenteEntity.class))).thenReturn(comitenteEntity);
        comitenteJdbcAdapter.saveComitente(comitenteDomain);

        verify(comitenteRepository, times(1)).save(any(ComitenteEntity.class));
    }
    @Test
    void saveComitenteFailsWhenDuplicate() {
        ComitenteDomain comitenteDomain = ComitenteDomain.builder().description("ComitenteDuplicado").build();

        when(comitenteRepository.save(any(ComitenteEntity.class))).thenThrow(new DataIntegrityViolationException("El comitente ya existe"));

        assertThrows(ElementExisteException.class, () -> comitenteJdbcAdapter.saveComitente(comitenteDomain));
    }
    @Test
    void getCountComitentesReturnsCorrectCount() {
        when(comitenteRepository.countAllComitentes()).thenReturn(5);

        int count = comitenteJdbcAdapter.getCountComitentes();

        assertEquals(5, count);
    }
    @Test
    void getComitenteInformationSuccess() {
        ComitenteEntity comitenteEntity = new ComitenteEntity();
        comitenteEntity.setDescription("Comitente6");
        when(comitenteRepository.findByDescription("Comitente6")).thenReturn(Optional.of(comitenteEntity));

        ComitenteDomain result = comitenteJdbcAdapter.getComitenteInformation(comitenteEntity);

        assertNotNull(result);
    }
    @Test
    void getComitenteInformationThrowsNotFoundExceptionWhenNotFound() {
        when(comitenteRepository.findByDescription(anyString())).thenReturn(Optional.empty());

        ComitenteEntity comitenteEntity = new ComitenteEntity();
        comitenteEntity.setDescription("ComitenteNoExistente");

        assertThrows(NotFoundException.class, () -> comitenteJdbcAdapter.getComitenteInformation(comitenteEntity));
    }

    @Test
    void deleteComitenteSuccess() {
        ComitenteDomain comitenteDomain = ComitenteDomain.builder().description("Comitente5").build();
        doNothing().when(comitenteRepository).delete(any(ComitenteEntity.class));

        comitenteJdbcAdapter.deleteComitente(comitenteDomain);

        verify(comitenteRepository, times(1)).delete(any(ComitenteEntity.class));
    }
}
