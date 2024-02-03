package com.challenge.decrypto.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
public class StatusDomain {
    private String country;
    private List<Map<String, Object>> market;
}