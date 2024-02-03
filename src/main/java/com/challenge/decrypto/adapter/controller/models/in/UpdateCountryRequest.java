package com.challenge.decrypto.adapter.controller.models.in;

import com.challenge.decrypto.application.port.in.PutModifyCountryPort;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateCountryRequest {
    @NotNull
    @Schema(description = "New name for country.")
    private String newName;
    @NotNull
    @Schema(description = "Old name for search country.")
    private String oldName;
    public PutModifyCountryPort.Command toCommand() {
        return PutModifyCountryPort.Command.builder()
                .oldName(oldName)
                .newName(newName)
                .build();
    }
}
