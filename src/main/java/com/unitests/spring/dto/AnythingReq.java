package com.unitests.spring.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AnythingReq {
    @NotBlank(message = "Request Id cannot be null or empty")
    private String requestId;
    @NotNull(message = "Content cannot be null")
    private String content;
}
