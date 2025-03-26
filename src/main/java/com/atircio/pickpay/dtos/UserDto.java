package com.atircio.pickpay.dtos;

import com.atircio.pickpay.entities.enums.UserType;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UserDto(

    @NotBlank(message = "Full name is required")
     String fullName,

    @NotBlank(message = "CPF is required")
    @Size(min = 11, max = 14, message = "CPF must be between 11 and 14 characters")
     String CPF,

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
     String email,

    @NotBlank(message = "Password is required")
     String password,

    @Positive
    BigDecimal balance,


    @NotNull(message = "User type is required")
     UserType userType

    ){



}
