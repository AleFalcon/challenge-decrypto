package com.challenge.decrypto.application.usecase;

import com.challenge.decrypto.application.port.in.PostCreateComitentePort;
import com.challenge.decrypto.application.port.out.ComitenteDataBase;
import com.challenge.decrypto.domain.ComitenteDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CreateComitenteUseCase implements PostCreateComitentePort {
    private final ComitenteDataBase comitenteDataBase;
    public CreateComitenteUseCase(ComitenteDataBase comitenteDataBase) {
        this.comitenteDataBase = comitenteDataBase;
    }
    @Override
    @CacheEvict(value = "stats", key = "#root.method.name")
    public void createComitente(Command command) {
        log.info(">> Ingreso al caso de uso de creación del comitente");
        comitenteDataBase.saveComitente(ComitenteDomain.builder().description(command.getDescription()).build());
        log.info("<< Finalización de creación del comitente.");
    }
}
