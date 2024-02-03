package com.challenge.decrypto.adapter.jdbc;

import com.challenge.decrypto.application.exception.ElementExisteException;
import com.challenge.decrypto.application.exception.NotFoundException;
import com.challenge.decrypto.application.port.out.CountryDataBase;
import com.challenge.decrypto.application.port.out.CountryRepository;
import com.challenge.decrypto.domain.CountryDomain;
import com.challenge.decrypto.domain.CountryEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class CountryJdbcAdapter implements CountryDataBase {
    private final CountryRepository countryRepository;
    public CountryJdbcAdapter(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }
    @Transactional(readOnly = true)
    @Override
    public List<CountryDomain> getAllInformation() {
        List<CountryDomain> countriesDomain = new ArrayList<>();
        List<CountryEntity> countries = countryRepository.findAllComitentesForMarketsByCountry();
        for(CountryEntity countryEntity : countries) {
            countriesDomain.add(CountryDomain.toDomain(countryEntity));
        }
        return countriesDomain;
    }
    @Transactional(readOnly = true)
    @Override
    public CountryDomain getInformationCountry(CountryEntity countryEntity) {
        return CountryDomain.toDomain(
                countryRepository.findByName(countryEntity.getName()).orElseThrow(()
                        -> new NotFoundException("Country not found"))
        );
    }
    @Override
    public void saveCountry(CountryDomain countryDomain){
        try {
            countryRepository.save(CountryEntity.fromDomain(countryDomain));
        }catch (DataIntegrityViolationException e){
            throw new ElementExisteException("The country already exists");
        }
    }
    @Override
    public void deleteCountry(CountryDomain countryDomain){
        countryRepository.delete(CountryEntity.fromDomain(countryDomain));
    }
}
