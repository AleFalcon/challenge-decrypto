package com.challenge.decrypto.adapter.jdbc;

import com.challenge.decrypto.application.exception.ElementExisteException;
import com.challenge.decrypto.application.exception.NotFoundException;
import com.challenge.decrypto.application.port.out.CountryDataBase;
import com.challenge.decrypto.application.port.out.CountryRepository;
import com.challenge.decrypto.domain.CountryDomain;
import com.challenge.decrypto.domain.CountryEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CountryJdbcAdapter implements CountryDataBase {
    private final CountryRepository countryRepository;
    public CountryJdbcAdapter(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }
    @Transactional(readOnly = true)
    @Override
    public List<CountryDomain> getAllInformation() {
        List<CountryDomain> countriesDomain = new ArrayList<>();
        log.info("<< Obtención de paises, mercados y comitentes unidos");
        List<CountryEntity> countries = countryRepository.findAllComitentesForMarketsByCountry();
        for(CountryEntity countryEntity : countries) {
            log.info("<< Conversión de país entidad a país dominio");
            countriesDomain.add(CountryDomain.toDomain(countryEntity));
        }
        return countriesDomain;
    }
    @Transactional(readOnly = true)
    @Override
    public CountryDomain getInformationCountry(CountryEntity countryEntity) {
        log.info("<< Obtención de país, por nombre");
        return CountryDomain.toDomain(
                countryRepository.findByName(countryEntity.getName()).orElseThrow(()
                        -> {
                    log.warn(">> País no encontrado");
                    throw new NotFoundException("Country not found");
                })
        );
    }
    @Override
    public void saveCountry(CountryDomain countryDomain){
        try {
            log.info("<< Inicio de guardado de país");
            countryRepository.save(CountryEntity.fromDomain(countryDomain));
        }catch (DataIntegrityViolationException e){
            log.info(">> El país ya existe");
            throw new ElementExisteException("The country already exists");
        }
    }
    @Override
    public void deleteCountry(CountryDomain countryDomain){
        log.info("<< Inicio de eliminación de país");
        countryRepository.delete(CountryEntity.fromDomain(countryDomain));
        log.info(">> Finalizó la eliminación de país");
    }
}
