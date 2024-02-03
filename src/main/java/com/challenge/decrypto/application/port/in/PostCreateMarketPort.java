package com.challenge.decrypto.application.port.in;

import lombok.Builder;
import lombok.Value;

public interface PostCreateMarketPort {
    void createMarket(Command command);
    @Value
    @Builder
    class Command {
        String code;
        String description;
        String countryName;
    }
}
