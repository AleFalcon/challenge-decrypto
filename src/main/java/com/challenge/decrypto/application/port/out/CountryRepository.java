package com.challenge.decrypto.application.port.out;

import com.challenge.decrypto.domain.CountryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository  extends CrudRepository<CountryEntity, Long> {
    @Query("SELECT c FROM CountryEntity c JOIN FETCH c.markets m JOIN FETCH m.comitentes")
    List<CountryEntity> findAllComitentesForMarketsByCountry();
    @Query("SELECT c FROM CountryEntity c JOIN FETCH c.markets m JOIN FETCH m.comitentes WHERE c.name = :name")
    List<CountryEntity> findAllComitentesForMarketsByCountryName(String name);
    Optional<CountryEntity> findByName(String name);
}
