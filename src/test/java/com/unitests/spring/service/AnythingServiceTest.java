package com.unitests.spring.service;

import com.unitests.spring.dto.AnythingReq;
import com.unitests.spring.mapper.AnythingEntity;
import com.unitests.spring.mapper.AnythingMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * service layer can be tested without spring.
 * Using mockito to mock external dependencies, just focus on service its own logic
 */
@ExtendWith(MockitoExtension.class)
class AnythingServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private AnythingMapper mapper;

    @Spy
    @InjectMocks
    private AnythingService anythingService;

    @Test
    void testGetAnything() {

        when(restTemplate.getForEntity(eq("https://httpbin.org/anything"), any()))
                .thenReturn(ResponseEntity.ok("Success from RestTemplate"));

        assertEquals("Success from RestTemplate", anythingService.getAnything());
    }

    @Test
    void testGetAnythingWithDb() {
        Map<String, String> response = new HashMap<>(1);
        response.put("uuid", "2148a8fb-92fb-47b4-adf9-9c718fa09970");
        when(restTemplate.getForEntity(eq("https://httpbin.org/uuid"), any()))
                .thenReturn(ResponseEntity.ok(response));

        AnythingReq anythingReq = new AnythingReq();
        anythingReq.setContent("test content");
        anythingReq.setRequestId("request id");

        when(mapper.insertNew(any())).thenReturn(1);
        when(mapper.selectOne("2148a8fb-92fb-47b4-adf9-9c718fa09970"))
                .thenReturn(AnythingEntity.builder().id("2148a8fb-92fb-47b4-adf9-9c718fa09970")
                        .requestId(anythingReq.getRequestId()).content(anythingReq.getContent()).build());

        AnythingEntity anythingWithDb = anythingService.getAnythingWithDb(anythingReq);

        assertEquals("2148a8fb-92fb-47b4-adf9-9c718fa09970", anythingWithDb.getId());

    }
}