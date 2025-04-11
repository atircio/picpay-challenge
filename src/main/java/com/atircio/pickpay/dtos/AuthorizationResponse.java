package com.atircio.pickpay.dtos;

public record AuthorizationResponse(

        String status,
        AuthorizationData data
) {
}
