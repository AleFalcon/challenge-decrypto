package com.challenge.decrypto.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
public class CountryDomain {
    private Long id;
    private String name;
    private Set<MarketDomain> markets;

    public static CountryDomain toDomain(CountryEntity countryEntity){
        Set<MarketDomain> marketsDomain = new HashSet<>();
        if(countryEntity.getMarkets() != null) {
            for (MarketEntity marketEntity : countryEntity.getMarkets()) {
                marketsDomain.add(MarketDomain.toDomain(marketEntity));
            }
        }
        return CountryDomain.builder()
                .id(countryEntity.getId())
                .name(countryEntity.getName())
                .markets(marketsDomain)
                .build();
    }
}
