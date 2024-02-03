package com.challenge.decrypto.adapter.controller.models.in;

import com.challenge.decrypto.application.port.in.PutModifyComitentePort;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UpdateComitenteRequest {
    @Schema(description = "New market for comitente.")
    private List<String> addMarkets;
    @Schema(description = "Remove market for comitente.")
    private List<String> deleteMarkets;
    public PutModifyComitentePort.Command toCommand(String comitenteDescription) {
        return PutModifyComitentePort.Command.builder()
                .description(comitenteDescription)
                .addMarkets(addMarkets)
                .deleteMarkets(deleteMarkets)
                .build();
    }
}
