package com.challenge.decrypto.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
public class MarketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String code;
    private String description;
    @ManyToMany(mappedBy = "markets")
    private Set<ComitenteEntity> comitentes;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryEntity country;
    public MarketEntity(String code, String description, CountryEntity countryEntity) {
        this.code = code;
        this.description = description;
        this.country = countryEntity;
        this.comitentes = new HashSet<>();
    }
    public MarketEntity() {}
    public void addComitente(ComitenteEntity comitenteEntity) {
        comitentes.add(comitenteEntity);
        comitenteEntity.getMarkets().add(this);
    }
}
