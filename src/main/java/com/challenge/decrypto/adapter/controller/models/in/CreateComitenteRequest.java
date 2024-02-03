package com.challenge.decrypto.adapter.controller.models.in;

import com.challenge.decrypto.application.port.in.PostCreateComitentePort;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateComitenteRequest {
    @NotNull
    @Schema(description = "description for comitente. This value is unique")
    private String description;
    public PostCreateComitentePort.Command toCommand() {
        return PostCreateComitentePort.Command.builder()
                .description(description)
                .build();
    }
}
