package com.challenge.decrypto.application.port.out;

import com.challenge.decrypto.domain.CountryDomain;
import com.challenge.decrypto.domain.CountryEntity;

import java.util.List;

public interface CountryDataBase {
    List<CountryDomain> getAllInformation();
    CountryDomain getInformationCountry(CountryEntity countryEntity);
    void saveCountry(CountryDomain countryDomain);
    void deleteCountry(CountryDomain countryDomain);
}
