package com.atircio.pickpay.dtos;

public record NotificationSenderResponse(

        String status,
        AuthorizationData data
) {
}
