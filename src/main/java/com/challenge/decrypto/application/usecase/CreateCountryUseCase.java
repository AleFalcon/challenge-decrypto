package com.challenge.decrypto.application.usecase;

import com.challenge.decrypto.application.port.in.PostCreateCountryPort;
import com.challenge.decrypto.application.port.out.CountryDataBase;
import com.challenge.decrypto.domain.CountryDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CreateCountryUseCase implements PostCreateCountryPort {
    private final CountryDataBase countryDataBase;
    public CreateCountryUseCase(CountryDataBase countryDataBase) {
        this.countryDataBase = countryDataBase;
    }
    @Override
    @CacheEvict(value = "stats", key = "#root.method.name")
    public void createCountry(Command command) {
        log.info(">> Ingreso al caso de uso de creación de país.");
        countryDataBase.saveCountry(CountryDomain.builder().name(command.getName()).build());
        log.info("<< Finalización de creación de país.");
    }
}
