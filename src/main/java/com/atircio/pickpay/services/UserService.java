package com.atircio.pickpay.services;

import com.atircio.pickpay.dtos.UserDto;
import com.atircio.pickpay.dtos.UserDtoResponse;
import com.atircio.pickpay.mappers.UserMapper;
import com.atircio.pickpay.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDtoResponse saveUser(UserDto dto) {
        return userMapper.userToUserDtoResponse(userRepository.save(userMapper.userDtoToUser(dto)));
    }

    public List<UserDtoResponse> findAllUsers() {
        return userMapper.userToUserDtoResponse(userRepository.findAll());
    }

    public UserDtoResponse findUserByCpf(String cpf) {
        return userMapper.userToUserDtoResponse(
                userRepository.findByCPF(cpf)
                        .orElseThrow(() -> new RuntimeException("User not found"))
        );
    }

    public UserDtoResponse findUserByEmail(String email) {
        return userMapper.userToUserDtoResponse(
                userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found"))
        );
    }
}
