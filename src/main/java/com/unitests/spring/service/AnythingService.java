package com.unitests.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AnythingService {

    private final RestTemplate okhttp3Template;

    private final String path = "https://httpbin.org/anything";

    public String getAnything() {
        String body = okhttp3Template.getForEntity("https://httpbin.org/anything", String.class).getBody();
        return body;
    }
}
