package com.challenge.decrypto.adapter.controller;

import com.challenge.decrypto.adapter.controller.models.in.CreateMarketRequest;
import com.challenge.decrypto.adapter.controller.models.in.UpdateMarketRequest;
import com.challenge.decrypto.adapter.controller.models.out.ResponseEnvelope;
import com.challenge.decrypto.application.port.in.DeleteMarketPort;
import com.challenge.decrypto.application.port.in.PostCreateMarketPort;
import com.challenge.decrypto.application.port.in.PutModifyMarketPort;
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
@Tag(name = "Market Controller", description = "CRUD for market ")
@RequestMapping("/market")
public class MarketController {
    private final PostCreateMarketPort postCreateMarketPort;
    private final PutModifyMarketPort putModifyMarketPort;
    private final DeleteMarketPort deleteMarketPort;
    public MarketController(PostCreateMarketPort postCreateMarketPort, PutModifyMarketPort putModifyMarketPort,
                             DeleteMarketPort deleteMarketPort) {
        this.postCreateMarketPort = postCreateMarketPort;
        this.putModifyMarketPort = putModifyMarketPort;
        this.deleteMarketPort = deleteMarketPort;
    }
    @PostMapping()
    public ResponseEnvelope<Void> createCountry(@RequestBody @Validated CreateMarketRequest createMarketRequest) {
        postCreateMarketPort.createMarket(createMarketRequest.toCommand());
        return new ResponseEnvelope<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase());
    }
    @PatchMapping()
    public ResponseEnvelope<Void> updateMarket(@RequestBody @Validated UpdateMarketRequest updateMarketRequest) {
        putModifyMarketPort.updatesMarket(updateMarketRequest.toCommand());
        return new ResponseEnvelope<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase());
    }
    @DeleteMapping("/{marketCode}")
    public ResponseEnvelope<Void> deleteMarket(@PathVariable String marketCode) {
        deleteMarketPort.deleteMarket(marketCode);
        return new ResponseEnvelope<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase());
    }
}
