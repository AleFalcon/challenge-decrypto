package com.challenge.decrypto.application.usecase;

import com.challenge.decrypto.application.port.in.PostCreateComitentePort;
import com.challenge.decrypto.application.port.out.ComitenteDataBase;
import com.challenge.decrypto.domain.ComitenteDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class CreateComitenteUseCaseTest {
    @Mock
    private ComitenteDataBase comitenteDataBase;
    @InjectMocks
    private CreateComitenteUseCase createComitenteUseCase;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void createComitenteSuccessfully() {
        PostCreateComitentePort.Command command = PostCreateComitentePort.Command.builder().description("Descripción del comitente").build();

        createComitenteUseCase.createComitente(command);

        verify(comitenteDataBase).saveComitente(any(ComitenteDomain.class));
    }
}

