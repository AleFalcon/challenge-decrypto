package com.challenge.decrypto.application.usecase;

import com.challenge.decrypto.application.port.out.ComitenteDataBase;
import com.challenge.decrypto.domain.ComitenteDomain;
import com.challenge.decrypto.domain.ComitenteEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

class DeleteComitenteTest {

    @Mock
    private ComitenteDataBase comitenteDataBase;

    @InjectMocks
    private DeleteComitente deleteComitente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteComitenteSuccessfully() {
        String description = "ComitenteTest";
        ComitenteDomain comitenteDomain = ComitenteDomain.builder().description(description).build();

        when(comitenteDataBase.getComitenteInformation(any(ComitenteEntity.class))).thenReturn(comitenteDomain);

        deleteComitente.deleteComitente(description);

        verify(comitenteDataBase).getComitenteInformation(any(ComitenteEntity.class));
        verify(comitenteDataBase).deleteComitente(comitenteDomain);
    }
}

