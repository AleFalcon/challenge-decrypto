package com.challenge.decrypto.application.port.in;

import lombok.Builder;
import lombok.Value;

public interface PutModifyMarketPort {
    void updatesMarket(Command command);

    @Value
    @Builder
    class Command {
        String code;
        String description;
        String country;

    }
}