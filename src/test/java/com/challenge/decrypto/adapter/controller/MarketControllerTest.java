package com.challenge.decrypto.adapter.controller;

import com.challenge.decrypto.adapter.controller.models.in.CreateMarketRequest;
import com.challenge.decrypto.application.exception.ElementExisteException;
import com.challenge.decrypto.application.exception.NotFoundException;
import com.challenge.decrypto.application.port.in.DeleteMarketPort;
import com.challenge.decrypto.application.port.in.PostCreateMarketPort;
import com.challenge.decrypto.application.port.in.PutModifyMarketPort;
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

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MarketController.class)
class MarketControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PostCreateMarketPort postCreateMarketPort;
    @MockBean
    private PutModifyMarketPort putModifyMarketPort;
    @MockBean
    private DeleteMarketPort deleteMarketPort;
    private static final String URI = "/market";
    @Test
    void testCreateMarketSuccess() throws Exception{
        CreateMarketRequest request = new CreateMarketRequest();
        request.setCode("AAA");
        request.setDescription("Super market");
        request.setCountry("Argentina");

        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(MockMvcResultMatchers.status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().string(containsString(HttpStatus.CREATED.getReasonPhrase())),
                        MockMvcResultMatchers.jsonPath("$.status").value(201),
                        MockMvcResultMatchers.jsonPath("$.message").value("Created"));

        verify(postCreateMarketPort, times(1)).createMarket(any(PostCreateMarketPort.Command.class));
    }
    @Test
    void testCreateMarketFail() throws Exception{
        doThrow(new ElementExisteException("The market already exists"))
                .when(postCreateMarketPort).createMarket(any(PostCreateMarketPort.Command.class));
        CreateMarketRequest request = new CreateMarketRequest();
        request.setCode("AAA");
        request.setDescription("Super market");
        request.setCountry("Argentina");

        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(MockMvcResultMatchers.status().isConflict(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.jsonPath("$.status").value(409),
                        MockMvcResultMatchers.jsonPath("$.message").value("The market already exists"),
                        MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty());

        verify(postCreateMarketPort, times(1)).createMarket(any(PostCreateMarketPort.Command.class));
    }
    @Test
    void testModifyMarketSuccess() throws Exception{
        CreateMarketRequest request = new CreateMarketRequest();
        request.setCode("AAA");
        request.setDescription("Super market");
        request.setCountry("Argentina");

        mockMvc.perform(patch(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(MockMvcResultMatchers.status().isNoContent(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().string(containsString(HttpStatus.NO_CONTENT.getReasonPhrase())),
                        MockMvcResultMatchers.jsonPath("$.status").value(204),
                        MockMvcResultMatchers.jsonPath("$.message").value("No Content"));

        verify(putModifyMarketPort, times(1)).updatesMarket(any(PutModifyMarketPort.Command.class));
    }
    @Test
    void testModifyMarketFailNotFound() throws Exception{
        doThrow(new NotFoundException("Market not found"))
                .when(putModifyMarketPort).updatesMarket(any(PutModifyMarketPort.Command.class));

        CreateMarketRequest request = new CreateMarketRequest();
        request.setCode("AAA");
        request.setDescription("Super market");
        request.setCountry("Argentina");

        mockMvc.perform(patch(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(MockMvcResultMatchers.status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.jsonPath("$.status").value(404),
                        MockMvcResultMatchers.jsonPath("$.message").value("Market not found"),
                        MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty());

        verify(putModifyMarketPort, times(1)).updatesMarket(any(PutModifyMarketPort.Command.class));
    }
    @Test
    void testDeleteMarketSuccess() throws Exception{
        mockMvc.perform(delete(URI + "/ROFEX")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(MockMvcResultMatchers.status().isNoContent(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().string(containsString(HttpStatus.NO_CONTENT.getReasonPhrase())),
                        MockMvcResultMatchers.jsonPath("$.status").value(204),
                        MockMvcResultMatchers.jsonPath("$.message").value("No Content"));

        verify(deleteMarketPort, times(1)).deleteMarket(any(String.class));
    }
    @Test
    void testDeleteComitenteNotFound() throws Exception{
        doThrow(new NotFoundException("Market not found"))
                .when(deleteMarketPort).deleteMarket(any(String.class));

        mockMvc.perform(delete(URI + "/AAA")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(MockMvcResultMatchers.status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.jsonPath("$.status").value(404),
                        MockMvcResultMatchers.jsonPath("$.message").value("Market not found"),
                        MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty());

        verify(deleteMarketPort, times(1)).deleteMarket(any(String.class));
    }
}
