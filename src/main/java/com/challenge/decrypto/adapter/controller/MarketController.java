package com.challenge.decrypto.adapter.controller;

import com.challenge.decrypto.adapter.controller.models.in.CreateMarketRequest;
import com.challenge.decrypto.adapter.controller.models.in.UpdateMarketRequest;
import com.challenge.decrypto.adapter.controller.models.out.ResponseEnvelope;
import com.challenge.decrypto.application.port.in.DeleteMarketPort;
import com.challenge.decrypto.application.port.in.PostCreateMarketPort;
import com.challenge.decrypto.application.port.in.PutModifyMarketPort;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@Slf4j
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
    public ResponseEntity<ResponseEnvelope<Void>> createMarket(@RequestBody @Validated CreateMarketRequest createMarketRequest) {
        log.info(">> Inicio de creación de mercado");
        postCreateMarketPort.createMarket(createMarketRequest.toCommand());
        log.info("<< Finalizó la creación de mercado");
        ResponseEnvelope<Void> envelope = new ResponseEnvelope<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase());
        return new ResponseEntity<>(envelope, HttpStatus.CREATED);
    }
    @PatchMapping()
    public ResponseEntity<ResponseEnvelope<Void>> updateMarket(@RequestBody @Validated UpdateMarketRequest updateMarketRequest) {
        log.info(">> Inicia de modificación del mercado: " + updateMarketRequest.getCode());
        putModifyMarketPort.updatesMarket(updateMarketRequest.toCommand());
        log.info("<< Finalizó la modificación del mercado");
        ResponseEnvelope<Void> envelope = new ResponseEnvelope<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase());
        return new ResponseEntity<>(envelope, HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/{marketCode}")
    public ResponseEntity<ResponseEnvelope<Void>> deleteMarket(@PathVariable String marketCode) {
        log.info(">> Inicio de eliminación de mercado: " + marketCode);
        deleteMarketPort.deleteMarket(marketCode);
        log.info("<< Finalizó la eliminación de mercado");
        ResponseEnvelope<Void> envelope = new ResponseEnvelope<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase());
        return new ResponseEntity<>(envelope, HttpStatus.NO_CONTENT);
    }
}
