package com.challenge.decrypto.application.usecase;

import com.challenge.decrypto.application.port.in.PostCreateCountryPort;
import com.challenge.decrypto.application.port.out.CountryDataBase;
import com.challenge.decrypto.domain.CountryDomain;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
public class CreateCountryUseCase implements PostCreateCountryPort {
    private final CountryDataBase countryDataBase;
    public CreateCountryUseCase(CountryDataBase countryDataBase) {
        this.countryDataBase = countryDataBase;
    }
    @Override
    @CacheEvict(value = "status", key = "#root.method.name")
    public void createCountry(Command command) {
        countryDataBase.saveCountry(CountryDomain.builder().name(command.getName()).build());
    }
}
