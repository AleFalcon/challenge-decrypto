package com.challenge.decrypto.adapter.jdbc;

import com.challenge.decrypto.application.exception.ElementExisteException;
import com.challenge.decrypto.application.exception.NotFoundException;
import com.challenge.decrypto.application.port.out.ComitenteDataBase;
import com.challenge.decrypto.application.port.out.ComitenteRepository;
import com.challenge.decrypto.domain.ComitenteDomain;
import com.challenge.decrypto.domain.ComitenteEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ComitenteJdbcAdapter implements ComitenteDataBase {
    private final ComitenteRepository comitenteRepository;

    public ComitenteJdbcAdapter(ComitenteRepository comitenteRepository) {
        this.comitenteRepository = comitenteRepository;
    }
    @Override
    public void saveComitente(ComitenteDomain comitenteDomain) {
        try {
            comitenteRepository.save(ComitenteEntity.fromDomain(comitenteDomain));
        } catch (DataIntegrityViolationException e) {
            throw new ElementExisteException("The comitente already exists");
        }
    }
    @Transactional(readOnly = true)
    @Override
    public int getCountComitentes() {
        return comitenteRepository.countAllComitentes();
    }
    @Transactional(readOnly = true)
    @Override
    public ComitenteDomain getComitenteInformation(ComitenteEntity comitenteEntity) {
        ComitenteEntity comitente = comitenteRepository.findByDescription(comitenteEntity.getDescription())
                .orElseThrow(() -> new NotFoundException("Comitente not found"));
        return ComitenteDomain.fromModel(comitente);
    }
    @Override
    public void deleteComitente(ComitenteDomain comitenteDomain){
        comitenteRepository.delete(ComitenteEntity.fromDomain(comitenteDomain));
    }
}
