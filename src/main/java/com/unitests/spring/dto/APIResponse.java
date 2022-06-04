package com.unitests.spring.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class APIResponse {
    private String status;
    private Object data;
    private List<Object> errors;

    public static APIResponse error(String status, List<Object> errors) {
        return APIResponse.builder().status(status).errors(errors).build();
    }

    public static APIResponse ok(Object data) {
        return APIResponse.builder().status("200").data(data).build();
    }

}
