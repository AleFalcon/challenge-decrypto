package com.challenge.decrypto.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
public class MarketDomain {
    private Long id;
    private String code;
    private String description;
    private Set<ComitenteDomain> comitentes;
    private CountryDomain country;
    public static MarketDomain toDomain(MarketEntity marketEntity) {
        Set<ComitenteDomain> comitenteDomain = new HashSet<>();
        for(ComitenteEntity comitenteEntity : marketEntity.getComitentes()){
            comitenteDomain.add(ComitenteDomain.toDomain(comitenteEntity));
        }
        return MarketDomain.builder()
                .id(marketEntity.getId())
                .code(marketEntity.getCode())
                .description(marketEntity.getDescription())
                .comitentes(comitenteDomain)
                .build();
    }
    public static MarketDomain fromModel(MarketEntity marketEntity) {
        return MarketDomain.builder()
                .id(marketEntity.getId())
                .code(marketEntity.getCode())
                .description(marketEntity.getDescription())
                .build();
    }
    public static MarketEntity toModel(MarketDomain marketDomain) {
        return new MarketEntity(marketDomain.getId(), marketDomain.getCode(), marketDomain.getDescription());
    }
}
