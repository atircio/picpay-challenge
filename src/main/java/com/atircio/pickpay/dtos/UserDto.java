package com.atircio.pickpay.dtos;

import com.atircio.pickpay.entities.enums.UserType;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class UserDto {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "CPF is required")
    @Size(min = 11, max = 14, message = "CPF must be between 11 and 14 characters")
    private String CPF;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Balance cannot be null")
    private BigDecimal balance;

    @NotNull(message = "User type is required")
    private UserType userType;

    public UserDto() {
    }

    public UserDto(String fullName, String CPF, String email, String password, BigDecimal balance, UserType userType) {
        this.fullName = fullName;
        this.CPF = CPF;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.userType = userType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "fullName='" + fullName + '\'' +
                ", CPF='" + CPF + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", userType=" + userType +
                '}';
    }


}
