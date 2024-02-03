package com.challenge.decrypto.adapter.controller;

import com.challenge.decrypto.adapter.controller.models.in.CreateCountryRequest;
import com.challenge.decrypto.adapter.controller.models.in.UpdateCountryRequest;
import com.challenge.decrypto.adapter.controller.models.out.ResponseEnvelope;
import com.challenge.decrypto.application.port.in.DeleteCountryPort;
import com.challenge.decrypto.application.port.in.PostCreateCountryPort;
import com.challenge.decrypto.application.port.in.PutModifyCountryPort;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@Tag(name = "Country Controller", description = "CRUD for Country ")
@RequestMapping("/country")
public class CountryController {
    private final PostCreateCountryPort postCreateCountryPort;
    private final PutModifyCountryPort putModifyCountryPort;
    private final DeleteCountryPort deleteCountryPort;
    public CountryController(PostCreateCountryPort postCreateCountryPort, PutModifyCountryPort putModifyCountryPort,
                               DeleteCountryPort deleteCountryPort) {
        this.postCreateCountryPort = postCreateCountryPort;
        this.putModifyCountryPort = putModifyCountryPort;
        this.deleteCountryPort = deleteCountryPort;
    }
    @PostMapping()
    public ResponseEnvelope<Void> createCountry(@RequestBody @Validated CreateCountryRequest createCountryRequest) {
        postCreateCountryPort.createCountry(createCountryRequest.toCommand());
        return new ResponseEnvelope<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase());
    }
    @PatchMapping()
    public ResponseEnvelope<Void> updateCountry(@RequestBody @Validated UpdateCountryRequest updateCountryRequest) {
        putModifyCountryPort.updatesCountry(updateCountryRequest.toCommand());
        return new ResponseEnvelope<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase());
    }
    @DeleteMapping("/{country}")
    public ResponseEnvelope<Void> deleteCountry(@PathVariable String country) {
        deleteCountryPort.deleteCountry(country);
        return new ResponseEnvelope<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase());
    }
}
