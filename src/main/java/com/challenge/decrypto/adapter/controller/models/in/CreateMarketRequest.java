package com.challenge.decrypto.adapter.controller.models.in;

import com.challenge.decrypto.application.port.in.PostCreateMarketPort;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateMarketRequest {
    @NotNull
    @Schema(description = "Code for market.")
    String code;
    @NotNull
    @Schema(description = "Description for market.")
    String description;
    @NotNull
    @Schema(description = "Country name for market.")
    String country;
    public PostCreateMarketPort.Command toCommand() {
        return PostCreateMarketPort.Command.builder()
                .code(code)
                .description(description)
                .countryName(country)
                .build();
    }
}
