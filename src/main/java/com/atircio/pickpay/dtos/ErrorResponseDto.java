package com.atircio.pickpay.dtos;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

public record ErrorResponseDto(
        int status,
        String message,
        LocalDateTime timestamp,
        Map<String, String> errors
) {
    public ErrorResponseDto(int status, String message) {
        this(status, message, LocalDateTime.now(), Collections.emptyMap()); // Empty immutable map
    }

    public ErrorResponseDto(int status, String message, Map<String, String> errors) {
        this(status, message, LocalDateTime.now(), Map.copyOf(errors)); // Immutable copy of map
    }
}
