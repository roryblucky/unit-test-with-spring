package com.unitests.spring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * service layer can be tested without spring.
 * Using mockito to mock external dependencies, just focus on service its own logic
 */
@ExtendWith(MockitoExtension.class)
class AnythingServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Spy
    @InjectMocks
    private AnythingService anythingService;

    @Test
    void testGetAnything() {

        when(restTemplate.getForEntity(anyString(), any()))
                .thenReturn(ResponseEntity.ok("Success from RestTemplate"));

        assertEquals("Success from RestTemplate", anythingService.getAnything());
    }
}