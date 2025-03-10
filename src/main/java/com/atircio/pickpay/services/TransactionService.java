package com.atircio.pickpay.services;

import com.atircio.pickpay.dtos.TransactionDto;
import com.atircio.pickpay.dtos.TransferMoneyRequestDto;
import com.atircio.pickpay.entities.Transaction;
import com.atircio.pickpay.entities.User;
import com.atircio.pickpay.entities.enums.TransactionStatus;
import com.atircio.pickpay.exceptions.InsufficientBalanceException;
import com.atircio.pickpay.exceptions.TransactionFailedException;
import com.atircio.pickpay.exceptions.UnauthorizedTransactionException;
import com.atircio.pickpay.mappers.TransactionMapper;
import com.atircio.pickpay.repositories.TransactionRepository;
import com.atircio.pickpay.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.math.BigDecimal;

public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository, TransactionMapper transactionMapper, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.userRepository = userRepository;
    }

    private final TransactionMapper transactionMapper;

    private final UserRepository userRepository;


    @Transactional
    public TransactionDto sendMoney(TransferMoneyRequestDto requestDto, String senderCpf) {
        User sender = userRepository.findByCPF(senderCpf).orElseThrow(
                () -> new EntityNotFoundException("Sender user not found with CPF: " + senderCpf));

        User receiver = userRepository.findByCPF(requestDto.cpfDestination()).orElseThrow(
                () -> new EntityNotFoundException("Receiver user not found with CPF: " + requestDto.cpfDestination()));

        if (sender.getBalance().compareTo(requestDto.amount()) < 0) {
            throw new InsufficientBalanceException("Not enough balance for this transaction.");
        }

        // Call External Authorization Service (Ensure the transaction is allowed)
        /*boolean isAuthorized = authorizationService.isTransactionAuthorized();
        if (!isAuthorized) {
            throw new UnauthorizedTransactionException("Transaction was not authorized.");
        }*/

        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setAmount(requestDto.amount());
        transaction.setStatus(TransactionStatus.PENDING);

        try {
            withdrawMoney(sender, requestDto.amount());
            depositMoney(receiver, requestDto.amount());

            transaction.setStatus(TransactionStatus.APPROVED);
            transactionRepository.save(transaction);

            // Notify Users (External Notification Service)
            //notificationService.notifyUser(receiver.getEmail(), "You have received a new transaction.");

            return transactionMapper.transactionToTransactionDto(transaction);
        } catch (Exception e) {
            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new TransactionFailedException("Transaction failed due to an internal error.");
        }
    }


    public void depositMoney(User user, BigDecimal amount) {
        user.setBalance(user.getBalance().add(amount));
    }
    public void withdrawMoney(User user, BigDecimal amount) {
        user.setBalance(user.getBalance().subtract(amount));
    }


}
