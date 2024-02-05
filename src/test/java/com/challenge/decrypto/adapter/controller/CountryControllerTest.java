package com.challenge.decrypto.adapter.controller;

import com.challenge.decrypto.adapter.controller.models.in.CreateCountryRequest;
import com.challenge.decrypto.adapter.controller.models.in.UpdateCountryRequest;
import com.challenge.decrypto.application.exception.ElementExisteException;
import com.challenge.decrypto.application.exception.NotFoundException;
import com.challenge.decrypto.application.port.in.DeleteCountryPort;
import com.challenge.decrypto.application.port.in.PostCreateCountryPort;
import com.challenge.decrypto.application.port.in.PutModifyCountryPort;
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
@WebMvcTest(CountryController.class)
class CountryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PostCreateCountryPort postCreateCountryPort;
    @MockBean
    private PutModifyCountryPort putModifyCountryPort;
    @MockBean
    private DeleteCountryPort deleteCountryPort;
    private static final String URI = "/country";
    @Test
    void testCreateCountrySuccess() throws Exception{
        CreateCountryRequest request = new CreateCountryRequest();
        request.setName("Brasil");

        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(MockMvcResultMatchers.status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().string(containsString(HttpStatus.CREATED.getReasonPhrase())),
                        MockMvcResultMatchers.jsonPath("$.status").value(201),
                        MockMvcResultMatchers.jsonPath("$.message").value("Created"));

        verify(postCreateCountryPort, times(1)).createCountry(any(PostCreateCountryPort.Command.class));
    }
    @Test
    void testCreateCountryFail() throws Exception{
        doThrow(new ElementExisteException("The country already exists"))
                .when(postCreateCountryPort).createCountry(any(PostCreateCountryPort.Command.class));
        CreateCountryRequest request = new CreateCountryRequest();
        request.setName("Brasil");

        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(MockMvcResultMatchers.status().isConflict(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.jsonPath("$.status").value(409),
                        MockMvcResultMatchers.jsonPath("$.message").value("The country already exists"),
                        MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty());

        verify(postCreateCountryPort, times(1)).createCountry(any(PostCreateCountryPort.Command.class));
    }
    @Test
    void testModifyCountrySuccess() throws Exception{
        UpdateCountryRequest request = new UpdateCountryRequest();
        request.setNewName("Brasil");
        request.setOldName("Uruguay");

        mockMvc.perform(patch(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(MockMvcResultMatchers.status().isNoContent(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().string(containsString(HttpStatus.NO_CONTENT.getReasonPhrase())),
                        MockMvcResultMatchers.jsonPath("$.status").value(204),
                        MockMvcResultMatchers.jsonPath("$.message").value("No Content"));

        verify(putModifyCountryPort, times(1)).updatesCountry(any(PutModifyCountryPort.Command.class));
    }
    @Test
    void testModifyCountryFailNotFound() throws Exception{
        doThrow(new NotFoundException("Country not found"))
                .when(putModifyCountryPort).updatesCountry(any(PutModifyCountryPort.Command.class));

        UpdateCountryRequest request = new UpdateCountryRequest();
        request.setNewName("Chile");
        request.setOldName("Brasil");

        mockMvc.perform(patch(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(MockMvcResultMatchers.status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.jsonPath("$.status").value(404),
                        MockMvcResultMatchers.jsonPath("$.message").value("Country not found"),
                        MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty());

        verify(putModifyCountryPort, times(1)).updatesCountry(any(PutModifyCountryPort.Command.class));
    }
    @Test
    void testDeleteCountrySuccess() throws Exception{
        mockMvc.perform(delete(URI + "/Uruguay")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(MockMvcResultMatchers.status().isNoContent(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().string(containsString(HttpStatus.NO_CONTENT.getReasonPhrase())),
                        MockMvcResultMatchers.jsonPath("$.status").value(204),
                        MockMvcResultMatchers.jsonPath("$.message").value("No Content"));

        verify(deleteCountryPort, times(1)).deleteCountry(any(String.class));
    }
    @Test
    void testDeleteCountryNotFound() throws Exception{
        doThrow(new NotFoundException("Country not found"))
                .when(deleteCountryPort).deleteCountry(any(String.class));

        mockMvc.perform(delete(URI + "/Brasil")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(MockMvcResultMatchers.status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.jsonPath("$.status").value(404),
                        MockMvcResultMatchers.jsonPath("$.message").value("Country not found"),
                        MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty());

        verify(deleteCountryPort, times(1)).deleteCountry(any(String.class));
    }
}
