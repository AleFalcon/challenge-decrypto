package com.challenge.decrypto.adapter.controller;

import com.challenge.decrypto.adapter.controller.models.in.CreateComitenteRequest;
import com.challenge.decrypto.adapter.controller.models.in.UpdateComitenteRequest;
import com.challenge.decrypto.adapter.controller.models.out.ResponseEnvelope;
import com.challenge.decrypto.application.port.in.DeleteComitentePort;
import com.challenge.decrypto.application.port.in.PostCreateComitentePort;
import com.challenge.decrypto.application.port.in.PutModifyComitentePort;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Tag(name = "Comitente Controller", description = "CRUD for Comitente ")
@RequestMapping("/comitente")
public class ComitenteController {
    private final PostCreateComitentePort postCreateComitentePort;
    private final PutModifyComitentePort putModifyComitentePort;
    private final DeleteComitentePort deleteComitentePort;

    public ComitenteController(PostCreateComitentePort postCreateComitentePort, PutModifyComitentePort putModifyComitentePort,
                               DeleteComitentePort deleteComitentePort) {
        this.postCreateComitentePort = postCreateComitentePort;
        this.putModifyComitentePort = putModifyComitentePort;
        this.deleteComitentePort = deleteComitentePort;
    }
    @PostMapping()
    public ResponseEnvelope<Void> createComitente(@RequestBody @Validated CreateComitenteRequest createComitenteRequest) {
        log.info(">> Inicio de creación del comitente");
        postCreateComitentePort.createComitente(createComitenteRequest.toCommand());
        log.info("Finalizó la creación del comitente");
        return new ResponseEnvelope<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase());
    }
    @PatchMapping("/{comitenteDescription}/markets")
    public ResponseEnvelope<Void> updateMarketsComitente(@PathVariable String comitenteDescription,
                                                       @RequestBody UpdateComitenteRequest createComitenteRequest) {
        log.info(">> Inicia de modificación de comitentes: " + comitenteDescription);
        putModifyComitentePort.updatesMarketsForComitente(createComitenteRequest.toCommand(comitenteDescription));
        log.info(">> Finalización de modificación del comitentes");
        return new ResponseEnvelope<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase());
    }
    @DeleteMapping ("/{comitenteDescription}")
    public ResponseEnvelope<Void> deleteComitente(@PathVariable String comitenteDescription) {
        log.info(">> Inicio de eliminación de comitente: " + comitenteDescription);
        deleteComitentePort.deleteComitente(comitenteDescription);
        log.info("<< Finalizó la eliminación del comitente");
        return new ResponseEnvelope<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase());
    }
}
