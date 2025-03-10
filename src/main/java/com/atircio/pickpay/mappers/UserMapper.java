package com.atircio.pickpay.mappers;

import com.atircio.pickpay.dtos.TransactionForUsersDto;
import com.atircio.pickpay.dtos.UserDto;
import com.atircio.pickpay.dtos.UserDtoResponse;
import com.atircio.pickpay.dtos.UserTransactionInfoResponse;
import com.atircio.pickpay.entities.Transaction;
import com.atircio.pickpay.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User userDtoToUser(UserDto dto) {
        return new User(dto.fullName(), dto.CPF(), dto.email(), dto.password(), dto.userType());
    }

    public UserDtoResponse userToUserDtoResponse(User entity) {
        return new UserDtoResponse(
                entity.getFullName(),
                entity.getEmail(),
                entity.getCPF(),
                entity.getBalance(),
                entity.getUserType(),
                mapTransactions(entity.getReceivedTransactions()),
                mapTransactions(entity.getSentTransactions())
        );
    }

    public List<UserDtoResponse> userToUserDtoResponse(List<User> users) {
        return users.stream()
                .map(this::userToUserDtoResponse)
                .collect(Collectors.toList());
    }

    public UserTransactionInfoResponse userToUserTransactionInfoResponse(User entity) {
        return new UserTransactionInfoResponse(entity.getFullName(), entity.getCPF());
    }

    private List<TransactionForUsersDto> mapTransactions(List<Transaction> transactions) {
        return transactions != null
                ? transactions.stream()
                .map(TransactionMapper::transactionToTransactionForUsersDto)
                .collect(Collectors.toList())
                : List.of();
    }
}
