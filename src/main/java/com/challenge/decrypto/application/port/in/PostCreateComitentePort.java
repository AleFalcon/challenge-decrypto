package com.challenge.decrypto.application.port.in;

import lombok.Builder;
import lombok.Value;

public interface PostCreateComitentePort {
    void createComitente(Command command);
    @Value
    @Builder
    class Command {
        String description;
    }
}
