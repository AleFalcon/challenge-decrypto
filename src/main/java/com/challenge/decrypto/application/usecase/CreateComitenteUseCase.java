package com.challenge.decrypto.application.usecase;

import com.challenge.decrypto.application.port.in.PostCreateComitentePort;
import com.challenge.decrypto.application.port.out.ComitenteDataBase;
import com.challenge.decrypto.domain.ComitenteDomain;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
public class CreateComitenteUseCase implements PostCreateComitentePort {
    private final ComitenteDataBase comitenteDataBase;
    public CreateComitenteUseCase(ComitenteDataBase comitenteDataBase) {
        this.comitenteDataBase = comitenteDataBase;
    }
    @Override
    @CacheEvict(value = "status", key = "#root.method.name")
    public void createComitente(Command command) {
        comitenteDataBase.saveComitente(ComitenteDomain.builder().description(command.getDescription()).build());
    }
}
