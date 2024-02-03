package com.challenge.decrypto.application.usecase;

import com.challenge.decrypto.application.port.in.DeleteComitentePort;
import com.challenge.decrypto.application.port.out.ComitenteDataBase;
import com.challenge.decrypto.domain.ComitenteDomain;
import com.challenge.decrypto.domain.ComitenteEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
public class DeleteComitente implements DeleteComitentePort {
    private final ComitenteDataBase comitenteDataBase;

    public DeleteComitente(ComitenteDataBase comitenteDataBase) {
        this.comitenteDataBase = comitenteDataBase;
    }

    @Override
    @CacheEvict(value = "status", key = "#root.method.name")
    public void deleteComitente(String description) {
        ComitenteDomain comitenteDomain = comitenteDataBase.getComitenteInformation(new ComitenteEntity(description));
        comitenteDataBase.deleteComitente(comitenteDomain);
    }
}
