package com.atircio.pickpay.dtos;

import com.atircio.pickpay.entities.User;
import com.atircio.pickpay.entities.enums.TransactionStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDto(
        UserTransactionInfoResponse sender,

        UserTransactionInfoResponse receiver,

        BigDecimal amount,

        String reference,

        TransactionStatus status,

        LocalDateTime createdAt


) {
}
