package com.challenge.decrypto.application.usecase;

import com.challenge.decrypto.application.port.in.PutModifyCountryPort;
import com.challenge.decrypto.application.port.out.CountryDataBase;
import com.challenge.decrypto.domain.CountryDomain;
import com.challenge.decrypto.domain.CountryEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
public class UpdateModifyCountryUseCase implements PutModifyCountryPort {
    private final CountryDataBase countryDataBase;
    public UpdateModifyCountryUseCase(CountryDataBase countryDataBase) {
        this.countryDataBase = countryDataBase;
    }
    @Override
    @CacheEvict(value = "status", key = "#root.method.name")
    public void updatesCountry(Command command) {
        CountryDomain countryDomain = countryDataBase.getInformationCountry(new CountryEntity(command.getOldName()));
        countryDomain.setName(command.getNewName());
        countryDataBase.saveCountry(countryDomain);
    }
}
