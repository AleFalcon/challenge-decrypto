package com.challenge.decrypto.adapter.controller.models.in;

import com.challenge.decrypto.application.port.in.PutModifyMarketPort;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateMarketRequest {
    @NotNull
    @Schema(description = "Code for market. This value is unique.")
    private String code;
    @Schema(description = "Description market.")
    private String description;
    @Schema(description = "Country of the market.")
    private String country;
    public PutModifyMarketPort.Command toCommand() {
        return PutModifyMarketPort.Command.builder()
                .code(code)
                .description(description)
                .country(country)
                .build();
    }
}
