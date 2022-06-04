package com.unitests.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unitests.spring.dto.AnythingReq;
import com.unitests.spring.support.JsonFileSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This is the integration tests for this spring application(happy path)
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UnitTestWithSpringApplicationTests {

    @Autowired
    private WebApplicationContext applicationContext;

    private MockMvc mockMvc;

    @BeforeAll
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    /**
     * Normal way. Construct request body in method
     */
    @Test
    void testAnything() throws Exception {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();

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
     * Normal way. Construct request body in method
     */
    @ParameterizedTest
    @JsonFileSource("anything-req.json")
    void testAnythingWithCallingService(String requestBody) throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/http/anythingWithCallingService")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print()) // print the request details
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.requestId").value("request_id_from_file"));
    }

}
