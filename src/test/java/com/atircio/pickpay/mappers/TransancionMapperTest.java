package com.atircio.pickpay.mappers;


import com.atircio.pickpay.dtos.TransactionDto;
import com.atircio.pickpay.dtos.TransactionForUsersDto;
import com.atircio.pickpay.entities.Transaction;
import com.atircio.pickpay.entities.User;
import com.atircio.pickpay.entities.enums.TransactionStatus;
import com.atircio.pickpay.entities.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransancionMapperTest {

    private TransactionMapper transactionMapper;

    @BeforeEach
    void setUp() {
        transactionMapper = new TransactionMapper();
    }

    @Test
    public void shouldMapTransactionToTransactionForUsersDto() {
        User sender = new User(
                "John Smith",
                "2323232323232",
                "john1244@gmail.com",
                "Pass1234",
                new BigDecimal("233434"),
                UserType.COMMON,
                new ArrayList<>(),
                new ArrayList<>()
        );

        User receiver = new User(
                "Ana Kipper",
                "23435465453",
                "anakipper@gmail.com",
                "Pass1234",
                new BigDecimal("1343"),
                UserType.COMMON,
                new ArrayList<>(),
                new ArrayList<>()
        );

        Transaction transaction = new Transaction(
                new BigDecimal(2000),
                TransactionStatus.APPROVED,
                sender,
                receiver
        );

        TransactionForUsersDto transactionForUsersDto = transactionMapper.transactionToTransactionForUsersDto(transaction);

        assertEquals(transactionForUsersDto.amount(), transaction.getAmount());
        assertEquals(transactionForUsersDto.reference(), transaction.getReference());
        assertEquals(transactionForUsersDto.createdAt(), transaction.getCreatedAt());
        assertEquals(transactionForUsersDto.status(), transaction.getStatus());

    }

    @Test
    public void shouldMapTransactionToTransactionDto(){
        User sender = new User(
                "John Smith",
                "2323232323232",
                "john1244@gmail.com",
                "Pass1234",
                new BigDecimal("233434"),
                UserType.COMMON,
                new ArrayList<>(),
                new ArrayList<>()
        );

        User receiver = new User(
                "Ana Kipper",
                "23435465453",
                "anakipper@gmail.com",
                "Pass1234",
                new BigDecimal("1343"),
                UserType.COMMON,
                new ArrayList<>(),
                new ArrayList<>()
        );

        Transaction transaction = new Transaction(
                new BigDecimal(2000),
                TransactionStatus.APPROVED,
                sender,
                receiver
        );

        TransactionDto dto = transactionMapper.transactionToTransactionDto(transaction);

        assertEquals(dto.amount(), transaction.getAmount());
        assertEquals(dto.reference(), transaction.getReference());
        assertEquals(dto.sender().fullName(), transaction.getSender().getFullName());
        assertEquals(dto.sender().cpf(), transaction.getSender().getCPF());
        assertEquals(dto.receiver().fullName(), transaction.getReceiver().getFullName());
        assertEquals(dto.receiver().cpf(), transaction.getReceiver().getCPF());
        assertEquals(dto.createdAt(), transaction.getCreatedAt());
        assertEquals(dto.status(), transaction.getStatus());

    }
}
