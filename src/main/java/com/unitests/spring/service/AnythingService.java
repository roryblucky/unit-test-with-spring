package com.unitests.spring.service;

import com.unitests.spring.dto.AnythingReq;
import com.unitests.spring.mapper.AnythingEntity;
import com.unitests.spring.mapper.AnythingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnythingService {

    private final RestTemplate okhttp3Template;
    private final AnythingMapper mapper;

    private final String path = "https://httpbin.org/anything";

    public String getAnything() {
        return okhttp3Template.getForEntity("https://httpbin.org/anything", String.class).getBody();
    }

    public AnythingEntity getAnythingWithDb(AnythingReq req) {
        Map<String, String> body = okhttp3Template.getForEntity("https://httpbin.org/uuid", Map.class).getBody();
        mapper.insertNew(AnythingEntity.builder()
                .id(body.get("uuid")).requestId(req.getRequestId()).content(req.getContent()).build());
        return mapper.selectOne(body.get("uuid"));
    }
}
