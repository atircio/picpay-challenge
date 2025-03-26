package com.atircio.pickpay.mappers;

import com.atircio.pickpay.dtos.TransactionDto;
import com.atircio.pickpay.dtos.TransactionForUsersDto;
import com.atircio.pickpay.dtos.UserTransactionInfoResponse;
import com.atircio.pickpay.entities.Transaction;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionMapper {


    public TransactionForUsersDto transactionToTransactionForUsersDto(Transaction entity){
        return new TransactionForUsersDto(entity.getAmount(), entity.getReference(),
                entity.getStatus(), entity.getCreatedAt()
                );
    }

    public TransactionDto transactionToTransactionDto(Transaction entity){
        return new TransactionDto(
                new UserTransactionInfoResponse(entity.getSender().getFullName(), entity.getSender().getCPF()),
                new UserTransactionInfoResponse(entity.getReceiver().getFullName(), entity.getReceiver().getCPF()),
                entity.getAmount(),
                entity.getReference(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }

}
