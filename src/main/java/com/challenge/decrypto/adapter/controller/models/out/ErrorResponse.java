package com.challenge.decrypto.adapter.controller.models.out;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {
    private int status;
    private String message;
    private long timestamp;
}

