package com.challenge.decrypto.adapter.controller;

import com.challenge.decrypto.adapter.controller.models.out.ResponseEnvelope;
import com.challenge.decrypto.adapter.controller.models.out.StatusResponse;
import com.challenge.decrypto.application.port.in.GetStatusPort;
import com.challenge.decrypto.domain.StatusDomain;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@Tag(name = "Information Controller", description = "Obtain information from database")
public class InformationController {
    private final GetStatusPort getStatusPort;

    public InformationController(GetStatusPort getStatusPort) {
        this.getStatusPort = getStatusPort;
    }

    @GetMapping("/status")
    @Operation(summary = "Get status", description = "Devuelve el porcentaje de comitentes por mercado que existen por país.")
    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseEnvelope.class),
            examples = @ExampleObject(value = "{\n" +
                    "    \"status\": 200,\n" +
                    "    \"message\": \"OK\",\n" +
                    "    \"data\": [\n" +
                    "        {\n" +
                    "            \"country\": \"Argentina\",\n" +
                    "            \"market\": [\n" +
                    "                {\n" +
                    "                    \"MAE\": {\n" +
                    "                        \"percentage\": \"100,00 %\"\n" +
                    "                    }\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"ROFEX\": {\n" +
                    "                        \"percentage\": \"50,00 %\"\n" +
                    "                    }\n" +
                    "                }\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"country\": \"Uruguay\",\n" +
                    "            \"market\": [\n" +
                    "                {\n" +
                    "                    \"UFEX\": {\n" +
                    "                        \"percentage\": \"100,00 %\"\n" +
                    "                    }\n" +
                    "                }\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}")))
    public ResponseEnvelope<List<StatusResponse>> getStatus() {
        List<StatusResponse> result = new ArrayList<>();
        List<StatusDomain> statuesDomain = getStatusPort.getStatus();
        for(StatusDomain statusDomain : statuesDomain) {
            result.add(StatusResponse.fromDomain(statusDomain));
        }

        return new ResponseEnvelope<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), result);
    }
}
