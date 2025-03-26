package com.atircio.pickpay.dtos;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record TransferMoneyRequestDto(
        @NotBlank(message = "CPF is required")
        @Size(min = 11, max = 14, message = "CPF must be between 11 and 14 characters")
        String cpfDestination,

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be greater than zero")
        BigDecimal amount
) {
}
