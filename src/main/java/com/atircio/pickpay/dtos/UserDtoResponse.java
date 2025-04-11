package com.atircio.pickpay.dtos;

import com.atircio.pickpay.entities.Transaction;
import com.atircio.pickpay.entities.enums.UserType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class UserDtoResponse {

    private String fullName;
    private String cpf;

    private String email;
    private BigDecimal balance;

    private UserType userType;

    private List<TransactionForUsersDto> SentTransactions = new ArrayList<>();

    private List<TransactionForUsersDto> ReceivedTransactions = new ArrayList<>();

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCPF() {
        return cpf;
    }

    public void setCPF(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<TransactionForUsersDto> getSentTransactions() {
        return SentTransactions;
    }

    public void setSentTransactions(List<TransactionForUsersDto> sentTransactions) {
        SentTransactions = sentTransactions;
    }

    public List<TransactionForUsersDto> getReceivedTransactions() {
        return ReceivedTransactions;
    }

    public void setReceivedTransactions(List<TransactionForUsersDto> receivedTransactions) {
        ReceivedTransactions = receivedTransactions;
    }
}
