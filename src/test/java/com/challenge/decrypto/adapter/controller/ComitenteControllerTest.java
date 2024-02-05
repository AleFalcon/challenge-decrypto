package com.challenge.decrypto.adapter.controller;

import com.challenge.decrypto.adapter.controller.models.in.CreateComitenteRequest;
import com.challenge.decrypto.adapter.controller.models.in.UpdateComitenteRequest;
import com.challenge.decrypto.application.exception.ElementExisteException;
import com.challenge.decrypto.application.exception.NotFoundException;
import com.challenge.decrypto.application.port.in.DeleteComitentePort;
import com.challenge.decrypto.application.port.in.PostCreateComitentePort;
import com.challenge.decrypto.application.port.in.PutModifyComitentePort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ComitenteController.class)
class ComitenteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PostCreateComitentePort postCreateComitentePort;
    @MockBean
    private PutModifyComitentePort putModifyComitentePort;
    @MockBean
    private DeleteComitentePort deleteComitentePort;
    private static final String URI = "/comitente";
    @Test
    void testCreateComitenteSuccess() throws Exception{
        CreateComitenteRequest request = new CreateComitenteRequest();
        request.setDescription("Comitente5");

        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(MockMvcResultMatchers.status().isCreated(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().string(containsString(HttpStatus.CREATED.getReasonPhrase())),
                MockMvcResultMatchers.jsonPath("$.status").value(201),
                MockMvcResultMatchers.jsonPath("$.message").value("Created"));

        verify(postCreateComitentePort, times(1)).createComitente(any(PostCreateComitentePort.Command.class));
    }
    @Test
    void testCreateComitenteFail() throws Exception{
        doThrow(new ElementExisteException("The comitente already exists"))
                .when(postCreateComitentePort).createComitente(any(PostCreateComitentePort.Command.class));
        CreateComitenteRequest request = new CreateComitenteRequest();
        request.setDescription("Comitente1");

        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(MockMvcResultMatchers.status().isConflict(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.jsonPath("$.status").value(409),
                        MockMvcResultMatchers.jsonPath("$.message").value("The comitente already exists"),
                        MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty());

        verify(postCreateComitentePort, times(1)).createComitente(any(PostCreateComitentePort.Command.class));
    }
    @Test
    void testModifyComitenteSuccess() throws Exception{
        UpdateComitenteRequest request = new UpdateComitenteRequest();
        List<String> addMarket = new ArrayList<>();
        addMarket.add("MAE");
        addMarket.add("ROFEX");
        request.setAddMarkets(addMarket);

        mockMvc.perform(patch(URI + "/Comitente5/markets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(MockMvcResultMatchers.status().isNoContent(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().string(containsString(HttpStatus.NO_CONTENT.getReasonPhrase())),
                        MockMvcResultMatchers.jsonPath("$.status").value(204),
                        MockMvcResultMatchers.jsonPath("$.message").value("No Content"));

        verify(putModifyComitentePort, times(1)).updatesMarketsForComitente(any(PutModifyComitentePort.Command.class));
    }
    @Test
    void testModifyComitenteFailNotFound() throws Exception{
        doThrow(new NotFoundException("Comitente not found"))
                .when(putModifyComitentePort).updatesMarketsForComitente(any(PutModifyComitentePort.Command.class));

        UpdateComitenteRequest request = new UpdateComitenteRequest();
        List<String> addMarket = new ArrayList<>();
        addMarket.add("MAE");
        addMarket.add("ROFEX");
        request.setAddMarkets(addMarket);

        mockMvc.perform(patch(URI + "/Comitente5/markets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(MockMvcResultMatchers.status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.jsonPath("$.status").value(404),
                        MockMvcResultMatchers.jsonPath("$.message").value("Comitente not found"),
                        MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty());

        verify(putModifyComitentePort, times(1)).updatesMarketsForComitente(any(PutModifyComitentePort.Command.class));
    }
    @Test
    void testDeleteComitenteSuccess() throws Exception{
        mockMvc.perform(delete(URI + "/Comitente5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(MockMvcResultMatchers.status().isNoContent(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().string(containsString(HttpStatus.NO_CONTENT.getReasonPhrase())),
                        MockMvcResultMatchers.jsonPath("$.status").value(204),
                        MockMvcResultMatchers.jsonPath("$.message").value("No Content"));

        verify(deleteComitentePort, times(1)).deleteComitente(any(String.class));
    }
    @Test
    void testDeleteComitenteNotFound() throws Exception{
        doThrow(new NotFoundException("Comitente not found"))
                .when(deleteComitentePort).deleteComitente(any(String.class));

        mockMvc.perform(delete(URI + "/Comitente5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(MockMvcResultMatchers.status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.jsonPath("$.status").value(404),
                        MockMvcResultMatchers.jsonPath("$.message").value("Comitente not found"),
                        MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty());

        verify(deleteComitentePort, times(1)).deleteComitente(any(String.class));
    }
}
