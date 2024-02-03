package com.challenge.decrypto.adapter.controller.models.out;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ResponseEnvelope<T> {
    @Schema(description = "Status code for operation.")
    private int status;
    @Schema(description = "Message for operation type.")
    private String message;
    @Schema(description = "Information of the application.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    public ResponseEnvelope(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public ResponseEnvelope(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
