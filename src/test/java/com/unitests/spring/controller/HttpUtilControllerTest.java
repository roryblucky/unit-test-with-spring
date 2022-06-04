package com.unitests.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unitests.spring.dto.AnythingReq;
import com.unitests.spring.mapper.AnythingEntity;
import com.unitests.spring.service.AnythingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This just a unit tests of controller, which include fields validation case and happy case
 * Note: @WebMvcTest will load all controllers, so we can have a base class and all tests inherited from it.
 */
@WebMvcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpUtilControllerTest {

    @Autowired
    private MockMvc mockMvc;
    //Since this is the unit test of controller, so we should mock all external dependencies
    //In this method, AnythingService is the external service, so we need to mock it by using Mockito
    @MockBean
    private AnythingService service;

    private final ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();

    /**
     * Happy path
     */
    @Test
    void testAnythingWithValidRequest() throws Exception {
        AnythingReq anythingReq = new AnythingReq();
        anythingReq.setRequestId(UUID.randomUUID().toString());
        anythingReq.setContent("123");

        mockMvc.perform(MockMvcRequestBuilders.post("/http/anything")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(anythingReq)))
                .andDo(print()) // print the request details
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"));
    }

    /**
     * unhappy path - fields validation
     */
    @Test
    void testAnythingWithEmptyRequestId() throws Exception {
        AnythingReq anythingReq = new AnythingReq();
        anythingReq.setContent("123");

        mockMvc.perform(MockMvcRequestBuilders.post("/http/anything")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(anythingReq)))
                .andDo(print()) // print the request details
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.errors").isNotEmpty());
    }

    /**
     * unhappy path - fields validation
     */
    @Test
    void testAnythingWithNullContent() throws Exception {
        AnythingReq anythingReq = new AnythingReq();

        mockMvc.perform(MockMvcRequestBuilders.post("/http/anything")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(anythingReq)))
                .andDo(print()) // print the request details
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.errors").isNotEmpty());
    }

    @Test
    void testAnythingWithCallingServiceWithValidReq() throws Exception {
        when(service.getAnythingWithDb(any()))
                .thenReturn(AnythingEntity.builder().id("123").content("Success from service").build());

        AnythingReq anythingReq = new AnythingReq();
        anythingReq.setRequestId(UUID.randomUUID().toString());
        anythingReq.setContent("123");

        mockMvc.perform(MockMvcRequestBuilders.post("/http/anythingWithCallingService")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(anythingReq)))
                .andDo(print()) // print the request details
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"))
                .andExpect(jsonPath("$.data.content").value("Success from service"));
    }

    @Test
    void testAnythingWithCallingServiceWithInvalidReq() throws Exception {

        AnythingReq anythingReq = new AnythingReq();

        mockMvc.perform(MockMvcRequestBuilders.post("/http/anythingWithCallingService")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(anythingReq)))
                .andDo(print()) // print the request details
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.errors").isNotEmpty());
    }


}