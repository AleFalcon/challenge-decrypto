package com.challenge.decrypto.adapter.controller.models.out;

import com.challenge.decrypto.domain.StatsDomain;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
public class StatsResponse {
    private String country;
    private List<Map<String, Object>> market;
    public static StatsResponse fromDomain(StatsDomain statsDomain) {
        return StatsResponse.builder()
                .country(statsDomain.getCountry())
                .market(statsDomain.getMarket())
                .build();
    }
}
