package com.atircio.pickpay.mappers;

import com.atircio.pickpay.dtos.TransactionForUsersDto;
import com.atircio.pickpay.entities.Transaction;
import org.springframework.stereotype.Service;

@Service
public class TransactionMapper {

    public TransactionForUsersDto transactionToTransactionForUsersDto(Transaction entity){
        return new TransactionForUsersDto(entity.getAmount(), entity.getReference(),
                entity.getStatus(), entity.getCreatedAt()
                );
    }

}
