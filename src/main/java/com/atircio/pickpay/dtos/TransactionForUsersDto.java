package com.atircio.pickpay.dtos;

import com.atircio.pickpay.entities.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionForUsersDto(

        BigDecimal amount,

        String reference,

        TransactionStatus status,

        LocalDateTime createdAt
) {
}
