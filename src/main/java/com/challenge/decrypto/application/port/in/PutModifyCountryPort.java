package com.challenge.decrypto.application.port.in;

import lombok.Builder;
import lombok.Value;

public interface PutModifyCountryPort {
    void updatesCountry(Command command);
    @Value
    @Builder
    class Command {
        String oldName;
        String newName;
    }
}
