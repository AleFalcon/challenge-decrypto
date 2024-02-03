package com.challenge.decrypto.application.port.in;

import lombok.Builder;
import lombok.Value;

public interface PutModifyMarketPort {
    void updatesMarket(Command command);

    //@Schema(description = "New name for country.")
    @Value
    @Builder
    class Command {
        String code;
        String description;
        String country;

    }
}