package com.atircio.pickpay.dtos;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public record ErrorResponseDto(
        int status,
        String message,
        LocalDateTime timestamp,
        HashMap<String, String> errors
) {

    public ErrorResponseDto(int status, String message){
        this(status, message, LocalDateTime.now(), new HashMap<>());
    }
    public ErrorResponseDto(int status, String message, HashMap<String, String> errors){
        this(status, message, LocalDateTime.now(), errors);
    }

}
