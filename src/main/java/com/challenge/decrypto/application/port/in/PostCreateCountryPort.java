package com.challenge.decrypto.application.port.in;

import lombok.Builder;
import lombok.Value;

public interface PostCreateCountryPort {
    void createCountry(Command command);
    @Value
    @Builder
    class Command {
        String name;
    }
}
