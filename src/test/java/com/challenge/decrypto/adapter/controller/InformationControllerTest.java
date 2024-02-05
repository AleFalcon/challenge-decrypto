package com.challenge.decrypto.adapter.controller;

import com.challenge.decrypto.application.port.in.GetStatsPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.challenge.decrypto.domain.StatsDomain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hibernate.internal.util.collections.CollectionHelper.listOf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@WebMvcTest(InformationController.class)
class InformationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GetStatsPort getStatsPort;
    private static final String URI = "/stats";
    @Test
    void testGetStatsSuccess() throws Exception{
        Map<String, Object> markets = new HashMap<String, Object>();
        markets.put("MAE", "100.00 %");
        StatsDomain sampleStats = StatsDomain.builder().country("Argentina").market(listOf(markets)).build();
        List<StatsDomain> statsList = List.of(sampleStats);

        when(getStatsPort.getStats()).thenReturn(statsList);

        mockMvc.perform(get(URI)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(MockMvcResultMatchers.status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.jsonPath("$.status").value(200),
                        MockMvcResultMatchers.jsonPath("$.message").value("OK"),
                        MockMvcResultMatchers.jsonPath("$.data").isNotEmpty()
                );

        verify(getStatsPort, times(1)).getStats();
    }
}
