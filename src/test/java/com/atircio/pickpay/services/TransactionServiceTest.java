package com.atircio.pickpay.services;

import com.atircio.pickpay.dtos.TransactionDto;
import com.atircio.pickpay.dtos.UserTransactionInfoResponse;
import com.atircio.pickpay.entities.Transaction;
import com.atircio.pickpay.entities.User;
import com.atircio.pickpay.entities.enums.TransactionStatus;
import com.atircio.pickpay.entities.enums.UserType;
import com.atircio.pickpay.mappers.TransactionMapper;
import com.atircio.pickpay.repositories.TransactionRepository;
import com.atircio.pickpay.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;


public class TransactionServiceTest {

    @InjectMocks
    TransactionService transactionService;
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    TransactionMapper transactionMapper;
    @Mock
    UserRepository userRepository;
    @Mock
    TransactionAuthorizationService transactionAuthorizationService;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnTransactionDto_whenSentMoney(){
        //Given
        UserTransactionInfoResponse receiver = new UserTransactionInfoResponse(
                "John Smith",
                "2323232323232"
        );

        UserTransactionInfoResponse sender = new UserTransactionInfoResponse(
                "Ligia Smith",
                "2422420002424"
        );

        String senderCpf = "2422420002424";

        TransactionDto transactionDto = new TransactionDto(
                sender,
                receiver,
                new BigDecimal(20),
                "239248294248",
                TransactionStatus.PENDING,
                LocalDateTime.now()
        );

        //Mock Calls


        //When

        //Then
    }
}
