package com.challenge.decrypto.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
public class ComitenteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String description;
    @ManyToMany
    @JoinTable(
            name = "comitente_market",
            joinColumns = @JoinColumn(name = "comitente_id"),
            inverseJoinColumns = @JoinColumn(name = "market_id")
    )
    private Set<MarketEntity> markets;
    public ComitenteEntity(){}
    public ComitenteEntity(String description){
        this.description= description;
        this.markets = new HashSet<>();
    }
    public ComitenteEntity(String description, Set<MarketEntity> markets){
        this.description= description;
        this.markets = markets;
    }
    public ComitenteEntity(Long id, String description, Set<MarketEntity> markets){
        this.id = id;
        this.description= description;
        this.markets = markets;
    }
    public static ComitenteEntity fromDomain(ComitenteDomain comitenteDomain) {
        Set<MarketEntity> marketsEntity = new HashSet<>();
        if (comitenteDomain.getMarkets() != null) {
            comitenteDomain.getMarkets().forEach(marketDomain ->
                    marketsEntity.add(new MarketEntity(marketDomain.getId()))
            );
        }
        return new ComitenteEntity(comitenteDomain.getId(), comitenteDomain.getDescription(), marketsEntity);
    }
}
