package com.challenge.decrypto.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Builder
@Setter
@Getter
public class ComitenteDomain {
    private Long id;
    private String description;
    private Set<MarketDomain> markets;
    public static ComitenteDomain fromModel(ComitenteEntity comitenteEntity){
        Set<MarketDomain> marketsDomain = new HashSet<>();
        if (comitenteEntity.getMarkets() != null) {
            for (MarketEntity market : comitenteEntity.getMarkets()) {
                marketsDomain.add(MarketDomain.fromModel(market));
            }
        }
        return ComitenteDomain.builder()
                .id(comitenteEntity.getId())
                .description(comitenteEntity.getDescription())
                .markets(marketsDomain)
                .build();
    }
    public static ComitenteDomain toDomain(ComitenteEntity comitenteEntity){
        return ComitenteDomain.builder()
                .id(comitenteEntity.getId())
                .description(comitenteEntity.getDescription())
                .build();
    }
}
