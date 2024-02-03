package com.challenge.decrypto.application.port.in;

import lombok.Builder;
import lombok.Value;

import java.util.List;

public interface PutModifyComitentePort {
    void updatesMarketsForComitente(Command command);
    @Value
    @Builder
    class Command {
        String description;
        List<String> addMarkets;
        List<String> deleteMarkets;
    }
}
