package com.atircio.pickpay.mappers;

import com.atircio.pickpay.dtos.TransactionForUsersDto;
import com.atircio.pickpay.dtos.UserDto;
import com.atircio.pickpay.dtos.UserDtoResponse;
import com.atircio.pickpay.entities.Transaction;
import com.atircio.pickpay.entities.User;
import com.atircio.pickpay.entities.enums.TransactionStatus;
import com.atircio.pickpay.entities.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserMapperTest {

    private TransactionMapper transactionMapper;
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        transactionMapper = mock(TransactionMapper.class);
        userMapper = new UserMapper(transactionMapper);
    }

    @Test
     void shouldMapUserDtoToUser(){
        UserDto dto = new UserDto(
                "John Smith",
                "2323232323232",
                "john1244@gmail.com",
                "Pass1234",
                new BigDecimal("233434"),
                UserType.COMMON
        );

        User user = userMapper.userDtoToUser(dto);

        assertNotNull(user);
        assertEquals(dto.fullName(), user.getFullName());
        assertEquals(dto.CPF(), user.getCPF());
        assertEquals(dto.email(), user.getEmail());
        assertEquals(dto.password(), user.getPassword());
        assertEquals(dto.userType(), user.getUserType());
        assertEquals(dto.balance(), user.getBalance());

    }

    @Test
    void shouldMapUserToUserDtoResponse(){

        Transaction transaction = new Transaction();

        TransactionForUsersDto mappedTransaction = new TransactionForUsersDto(
                new BigDecimal("100.00"),
                "TEST_REF",
                TransactionStatus.APPROVED,
                LocalDateTime.now()
        );

        when(transactionMapper.transactionToTransactionForUsersDto(any(Transaction.class))).thenReturn(
                mappedTransaction
        );



        User user = new User(
                "John Smith",
                "2323232323232",
                "john1244@gmail.com",
                "Pass1234",
                new BigDecimal("233434"),
                UserType.COMMON,
                List.of(transaction),
                List.of(transaction)
        );

        UserDtoResponse response = userMapper.userToUserDtoResponse(user);

        assertNotNull(response);
        assertEquals(response.getFullName(), user.getFullName());
        assertEquals(response.getCPF(), user.getCPF());
        assertEquals(response.getEmail(), user.getEmail());
        assertEquals(response.getBalance(), user.getBalance());
        assertEquals(response.getUserType(), user.getUserType());

        assertEquals(1, user.getSentTransactions().size());
        assertEquals(1,user.getReceivedTransactions().size());

        verify(transactionMapper, times(2)).transactionToTransactionForUsersDto(transaction);


    }
}
