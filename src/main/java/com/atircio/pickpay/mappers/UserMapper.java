package com.atircio.pickpay.mappers;

import com.atircio.pickpay.dtos.UserDto;
import com.atircio.pickpay.dtos.UserDtoResponse;
import com.atircio.pickpay.dtos.UserTransactionInfoResponse;
import com.atircio.pickpay.entities.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class UserMapper {

    private final TransactionMapper transactionMapper;

    public UserMapper(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    public User userDtoToUser(UserDto dto){

        User entity = new User();
        entity.setFullName(dto.fullName());
        entity.setCPF(dto.CPF());
        entity.setEmail(dto.email());
        entity.setPassword(dto.password());
        entity.setUserType(dto.userType());
        entity.setBalance(dto.balance());

        return entity;
    }

    public UserDtoResponse userToUserDtoResponse(User entity) {
        UserDtoResponse dto = new UserDtoResponse();

        dto.setFullName(entity.getFullName());
        dto.setEmail(entity.getEmail());
        dto.setCPF(entity.getCPF());
        dto.setBalance(entity.getBalance());
        dto.setUserType(entity.getUserType());

        dto.setReceivedTransactions(Optional.ofNullable(entity.getReceivedTransactions())
                .map(transactions -> transactions.stream()
                        .map(transactionMapper::transactionToTransactionForUsersDto)
                        .toList())
                .orElse(List.of()));


        dto.setSentTransactions(entity.getSentTransactions() != null
                ? entity.getSentTransactions().stream()
                .map(transactionMapper::transactionToTransactionForUsersDto)
                .toList()
                : Collections.emptyList());
        return dto;

    }

    public List<UserDtoResponse> userToUserDtoResponse(List<User> users) {
       return users.stream().map(this::userToUserDtoResponse).toList();
    }

    public UserTransactionInfoResponse userToUserTransactionInfoResponse(User entity){
        return new UserTransactionInfoResponse(entity.getFullName(),entity.getCPF());
    }
}
