package com.challenge.decrypto.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "country")
    private Set<MarketEntity> markets;
    public CountryEntity(String name) {
        this.name = name;
    }
    public CountryEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public CountryEntity() {}
    public static CountryEntity fromDomain(CountryDomain countryDomain) {
        return new CountryEntity(countryDomain.getId(), countryDomain.getName());
    }
}
