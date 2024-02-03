package com.challenge.decrypto.adapter.controller.models.out;

import com.challenge.decrypto.domain.StatusDomain;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
public class StatusResponse {
    private String country;
    private List<Map<String, Object>> market;
    public static StatusResponse fromDomain(StatusDomain statusDomain) {
        return StatusResponse.builder()
                .country(statusDomain.getCountry())
                .market(statusDomain.getMarket())
                .build();
    }
}
