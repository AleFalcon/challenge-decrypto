package com.challenge.decrypto.adapter.controller.models.in;

import com.challenge.decrypto.application.port.in.PostCreateCountryPort;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCountryRequest {
    @NotNull
    @Schema(description = "Name for country. This value is unique")
    private String name;
    public PostCreateCountryPort.Command toCommand() {
        return PostCreateCountryPort.Command.builder()
                .name(name)
                .build();
    }
}
