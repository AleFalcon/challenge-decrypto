package com.challenge.decrypto.application.usecase;

import com.challenge.decrypto.application.port.in.DeleteComitentePort;
import com.challenge.decrypto.application.port.out.ComitenteDataBase;
import com.challenge.decrypto.domain.ComitenteDomain;
import com.challenge.decrypto.domain.ComitenteEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeleteComitente implements DeleteComitentePort {
    private final ComitenteDataBase comitenteDataBase;

    public DeleteComitente(ComitenteDataBase comitenteDataBase) {
        this.comitenteDataBase = comitenteDataBase;
    }

    @Override
    @CacheEvict(value = "stats", key = "#root.method.name")
    public void deleteComitente(String description) {
        log.info(">> Ingreso al caso de uso de eliminación de comitente.");
        ComitenteDomain comitenteDomain = comitenteDataBase.getComitenteInformation(new ComitenteEntity(description));
        comitenteDataBase.deleteComitente(comitenteDomain);
        log.info("<< Finalizó la ejecución del CU de eliminación de comitente.");
    }
}
