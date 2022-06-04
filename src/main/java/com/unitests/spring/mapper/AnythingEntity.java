package com.unitests.spring.mapper;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnythingEntity {
    private String id;
    private String requestId;
    private String content;
}
