package com.atircio.pickpay.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferMoneyRequestDto(
        @NotBlank(message = "CPF destination is required")
        @Pattern(regexp = "\\d{11}", message = "CPF must be 11 digits")
        String cpfDestination,

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be greater than zero")
        BigDecimal amount
) {
}
