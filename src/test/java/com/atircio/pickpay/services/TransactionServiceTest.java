package com.atircio.pickpay.services;

import com.atircio.pickpay.dtos.*;
import com.atircio.pickpay.entities.Transaction;
import com.atircio.pickpay.entities.User;
import com.atircio.pickpay.entities.enums.TransactionStatus;
import com.atircio.pickpay.entities.enums.UserType;
import com.atircio.pickpay.exceptions.InsufficientBalanceException;
import com.atircio.pickpay.exceptions.UnauthorizedTransactionException;
import com.atircio.pickpay.mappers.TransactionMapper;
import com.atircio.pickpay.repositories.TransactionRepository;
import com.atircio.pickpay.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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

    }

    @Test
    void shouldReturnTransactionDto_whenSentMoney(){
        //Given
        String senderCpf = "123456765432";
        String receiverCpf = "45245422346";
        BigDecimal amount = new BigDecimal("100");

        TransferMoneyRequestDto requestDto = new TransferMoneyRequestDto(receiverCpf, amount);

        User sender = new User("Sender", senderCpf, "sender@email.com", "pass", new BigDecimal("200"), UserType.COMMON, new ArrayList<>(), new ArrayList<>());
        User receiver = new User("Receiver", receiverCpf, "receiver@email.com", "pass", new BigDecimal("50"), UserType.COMMON, new ArrayList<>(), new ArrayList<>());

        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setAmount(amount);
        transaction.setStatus(TransactionStatus.APPROVED);

        TransactionDto expectedDto = new TransactionDto(
                new UserTransactionInfoResponse(sender.getFullName(), sender.getCPF()),
                new UserTransactionInfoResponse(receiver.getFullName(), receiver.getCPF()),
                transaction.getAmount(),
                transaction.getReference(),
                transaction.getStatus(),
                transaction.getCreatedAt()
        );


        //Mock Calls
        when(userRepository.findByCPF(senderCpf)).thenReturn(Optional.of(sender));
        when(userRepository.findByCPF(receiverCpf)).thenReturn(Optional.of(receiver));
        when(transactionAuthorizationService.verifyTransaction()).thenReturn(new AuthorizationResponse("success",
                new AuthorizationData(true)));
        when(transactionRepository.save(transaction)).thenReturn(any(Transaction.class));
        when(transactionMapper.transactionToTransactionDto(transaction)).thenReturn(expectedDto);

        //When

        TransactionDto dto = transactionService.sendMoney(requestDto, senderCpf);

        //Then

        assertEquals(requestDto.amount(), dto.amount());
        assertEquals(requestDto.cpfDestination(), dto.receiver().cpf());
        verify(userRepository, times(1)).findByCPF(senderCpf);
        verify(userRepository, times(1)).findByCPF(receiverCpf);
        verify(transactionAuthorizationService, times(1)).verifyTransaction();
        verify(transactionRepository, times(1)).save(transaction);
        verify(transactionMapper, times(1)).transactionToTransactionDto(transaction);
    }

    @Test
    void shouldThrowAnUnauthorizedTransactionException_whenSenderAndReceiverAreSame(){
        //Given
        String senderCpf = "12345678910";

        TransferMoneyRequestDto requestDto = new TransferMoneyRequestDto("12345678910", new BigDecimal(1000));

        //Mock Calls
        UnauthorizedTransactionException exception = assertThrows(UnauthorizedTransactionException.class,
                ()-> transactionService.sendMoney(requestDto, senderCpf));


        assertEquals("The sender and receiver cannot have the same cpf", exception.getMessage());
        verify(userRepository, never()).findByCPF(senderCpf);
        verify(userRepository, never()).findByCPF(requestDto.cpfDestination());
        verify(transactionAuthorizationService, never()).verifyTransaction();


    }

    @Test
    void shouldThrowAnEntityNotFoundException_whenUserNotFound(){
        //Given
        String senderCpf = "12345678910";

        TransferMoneyRequestDto requestDto = new TransferMoneyRequestDto("123223678910", new BigDecimal(1000));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> transactionService.sendMoney(requestDto, senderCpf));

        verify(transactionAuthorizationService, never()).verifyTransaction();
    }

    @Test
    void shouldThrowAnIllegalArgumentException_whenSenderIsMarchant(){
        //Given
        String senderCpf = "12345678910";
        String receiverCpf = "43434343433";
        TransferMoneyRequestDto requestDto = new TransferMoneyRequestDto("43434343433", new BigDecimal(1000));

        User sender = new User("Sender", senderCpf, "sender@email.com", "pass", new BigDecimal("50"), UserType.MERCHANT, new ArrayList<>(), new ArrayList<>());
        User receiver = new User("Receiver", receiverCpf, "receiver@email.com", "pass", new BigDecimal("50"), UserType.COMMON, new ArrayList<>(), new ArrayList<>());

        //Mock calls

        when(userRepository.findByCPF(senderCpf)).thenReturn(Optional.of(sender));
        when(userRepository.findByCPF(receiverCpf)).thenReturn(Optional.of(receiver));

        //When

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> transactionService.sendMoney(requestDto, senderCpf));




    }

    @Test
    void shouldThrowAnInsufficientBalanceException_whenSenderDoNotHaveEnoughMoney(){
        //Given
        String senderCpf = "12345678910";

        User sender = new User("Sender", senderCpf, "sender@email.com", "pass", new BigDecimal(1), UserType.COMMON, new ArrayList<>(), new ArrayList<>());
        User receiver = new User("Receiver", "12345654334", "receiver@email.com", "pass", new BigDecimal(50), UserType.MERCHANT, new ArrayList<>(), new ArrayList<>());

        TransferMoneyRequestDto requestDto = new TransferMoneyRequestDto(receiver.getCPF(),new BigDecimal(100));

        //Mock calls
        when(userRepository.findByCPF(senderCpf)).thenReturn(Optional.of(sender));
        when(userRepository.findByCPF(receiver.getCPF())).thenReturn(Optional.of(receiver));

        //When
        InsufficientBalanceException exception = assertThrows(InsufficientBalanceException.class,
                () -> transactionService.sendMoney(requestDto, senderCpf));


    }

    @Test
    void shouldThrowAnUnauthorizedTransactionException_whenTransactionWasNotAuthorized() {
        //Given
        String senderCpf = "12345678910";
        User sender = new User("Sender", senderCpf, "sender@email.com", "pass", new BigDecimal(10000), UserType.COMMON, new ArrayList<>(), new ArrayList<>());
        User receiver = new User("Receiver", "12345654334", "receiver@email.com", "pass", new BigDecimal(50), UserType.MERCHANT, new ArrayList<>(), new ArrayList<>());

        TransferMoneyRequestDto requestDto = new TransferMoneyRequestDto(receiver.getCPF(), new BigDecimal(100));

        //Mock calls
        when(userRepository.findByCPF(senderCpf)).thenReturn(Optional.of(sender));
        when(userRepository.findByCPF((receiver.getCPF()))).thenReturn(Optional.of(receiver));
        when(transactionAuthorizationService.verifyTransaction()).thenReturn(
                new AuthorizationResponse("failed", new AuthorizationData(false))
        );

        //When
        UnauthorizedTransactionException exception = assertThrows(UnauthorizedTransactionException.class,
                () -> transactionService.sendMoney(requestDto, senderCpf));

    }

}
