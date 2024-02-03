package com.challenge.decrypto.adapter.jdbc;

import com.challenge.decrypto.application.exception.ElementExisteException;
import com.challenge.decrypto.application.exception.NotFoundException;
import com.challenge.decrypto.application.port.out.ComitenteDataBase;
import com.challenge.decrypto.application.port.out.ComitenteRepository;
import com.challenge.decrypto.domain.ComitenteDomain;
import com.challenge.decrypto.domain.ComitenteEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class ComitenteJdbcAdapter implements ComitenteDataBase {
    private final ComitenteRepository comitenteRepository;

    public ComitenteJdbcAdapter(ComitenteRepository comitenteRepository) {
        this.comitenteRepository = comitenteRepository;
    }
    @Override
    public void saveComitente(ComitenteDomain comitenteDomain) {
        try {
            log.info(">> Inicio de guardado de comitente");
            comitenteRepository.save(ComitenteEntity.fromDomain(comitenteDomain));
        } catch (DataIntegrityViolationException e) {
            log.warn("<< Fallo el guardado. El comitente que existe");
            throw new ElementExisteException("The comitente already exists");
        }
    }
    @Transactional(readOnly = true)
    @Override
    public int getCountComitentes() {
        log.info(">> Obtención de la cantidad total de comitentes");
        return comitenteRepository.countAllComitentes();
    }
    @Transactional(readOnly = true)
    @Override
    public ComitenteDomain getComitenteInformation(ComitenteEntity comitenteEntity) {
        log.info(">> Inicio de obtención de información de un comitente");
        ComitenteEntity comitente = comitenteRepository.findByDescription(comitenteEntity.getDescription())
                .orElseThrow(() -> {
                    log.warn("<< No se encontro el comitente");
                    throw new NotFoundException("Comitente not found");
                });
        return ComitenteDomain.fromModel(comitente);
    }
    @Override
    public void deleteComitente(ComitenteDomain comitenteDomain){
        log.info(">> Inicio de eliminación de comitente");
        comitenteRepository.delete(ComitenteEntity.fromDomain(comitenteDomain));
        log.info("<< Finalizó la eliminación del comitente");
    }
}
