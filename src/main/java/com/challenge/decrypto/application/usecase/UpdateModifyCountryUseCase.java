package com.challenge.decrypto.application.usecase;

import com.challenge.decrypto.application.port.in.PutModifyCountryPort;
import com.challenge.decrypto.application.port.out.CountryDataBase;
import com.challenge.decrypto.domain.CountryDomain;
import com.challenge.decrypto.domain.CountryEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UpdateModifyCountryUseCase implements PutModifyCountryPort {
    private final CountryDataBase countryDataBase;
    public UpdateModifyCountryUseCase(CountryDataBase countryDataBase) {
        this.countryDataBase = countryDataBase;
    }
    @Override
    @CacheEvict(value = "stats", key = "#root.method.name")
    public void updatesCountry(Command command) {
        log.info(">> Ingreso al caso de uso de modificación de pais.");
        CountryDomain countryDomain = countryDataBase.getInformationCountry(new CountryEntity(command.getOldName()));
        countryDomain.setName(command.getNewName());
        countryDataBase.saveCountry(countryDomain);
        log.info("<< Finalizó la ejecución del caso de uso de modificación de pais.");
    }
}
