package com.unitests.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unitests.spring.dto.AnythingReq;
import org.checkerframework.checker.units.qual.A;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This just a unit tests of controller, which include fields validation case and happy case
 * Note: @WebMvcTest will load all controllers, so we can have a base class and all tests inherited from it.
 */
@WebMvcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpUtilControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();;

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
}