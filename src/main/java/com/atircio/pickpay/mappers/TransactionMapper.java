package com.atircio.pickpay.mappers;

import com.atircio.pickpay.dtos.TransactionDto;
import com.atircio.pickpay.dtos.TransactionForUsersDto;
import com.atircio.pickpay.entities.Transaction;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionMapper {

    private final UserMapper userMapper;

    public TransactionMapper(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    public TransactionForUsersDto transactionToTransactionForUsersDto(Transaction entity){
        return new TransactionForUsersDto(entity.getAmount(), entity.getReference(),
                entity.getStatus(), entity.getCreatedAt()
                );
    }

    public TransactionDto transactionToTransactionDto(Transaction entity){
        return new TransactionDto(
                userMapper.userToUserTransactionInfoResponse(entity.getSender()),
                userMapper.userToUserTransactionInfoResponse(entity.getReceiver()),
                entity.getAmount(),
                entity.getReference(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }

}
