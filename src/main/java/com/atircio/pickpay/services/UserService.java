package com.atircio.pickpay.services;

import com.atircio.pickpay.dtos.UserDto;
import com.atircio.pickpay.dtos.UserDtoResponse;
import com.atircio.pickpay.entities.User;
import com.atircio.pickpay.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDtoResponse saveUser(UserDto dto) {
        return userToUserDtoResponse(userRepository.save(userDtoToUser(dto)));
    }

    public User userDtoToUser(UserDto dto){

        User entity = new User();
        entity.setFullName(dto.getFullName());
        entity.setCPF(dto.getCPF());
        entity.setEmail(dto.getEmail());
        entity.setBalance(dto.getBalance());
        entity.setPassword(dto.getPassword());
        entity.setUserType(dto.getUserType());

        return entity;
    }

    public UserDtoResponse userToUserDtoResponse(User entity){
        UserDtoResponse dto = new UserDtoResponse();

        dto.setFullName(entity.getFullName());
        dto.setEmail(entity.getEmail());
        dto.setCPF(entity.getCPF());
        dto.setBalance(entity.getBalance());
        dto.setUserType(entity.getUserType());
        dto.setReceivedTransactions(entity.getReceivedTransactions());
        dto.setSentTransactions(entity.getSentTransactions());


        return dto;
    }

}
