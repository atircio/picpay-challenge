package com.atircio.pickpay.services;

import com.atircio.pickpay.dtos.TransactionForUsersDto;
import com.atircio.pickpay.dtos.UserDto;
import com.atircio.pickpay.dtos.UserDtoResponse;
import com.atircio.pickpay.entities.Transaction;
import com.atircio.pickpay.entities.User;
import com.atircio.pickpay.entities.enums.TransactionStatus;
import com.atircio.pickpay.entities.enums.UserType;
import com.atircio.pickpay.mappers.UserMapper;
import com.atircio.pickpay.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;


    @BeforeEach
    void setUp(){

    }

    @Test
    void should_sucessfully_save_an_user(){
        //Given
        UserDto dto = new UserDto(
                "John Smith",
                "2323232323232",
                "john1244@gmail.com",
                "Pass1234",
                new BigDecimal("233434"),
                UserType.COMMON
        );

        User user = new User(
                "John Smith",
                "2323232323232",
                "john1244@gmail.com",
                "Pass1234",
                new BigDecimal("233434"),
                UserType.COMMON,
                List.of(new Transaction()),
                List.of(new Transaction())
        );

        User savedUser = new User(
                "John Smith",
                "2323232323232",
                "john1244@gmail.com",
                "Pass1234",
                new BigDecimal("233434"),
                UserType.COMMON,
                List.of(new Transaction()),
                List.of(new Transaction())
        );

        UserDtoResponse dtoResponse = new UserDtoResponse(
                "John Smith",
                "2323232323232",
                "john1244@gmail.com",
                new BigDecimal("233434"),
                UserType.COMMON,
                List.of(new TransactionForUsersDto(new BigDecimal(200), "3524242342", TransactionStatus.APPROVED,  LocalDateTime.now())),
                List.of(new TransactionForUsersDto(new BigDecimal(200), "3524242342", TransactionStatus.APPROVED,  LocalDateTime.now()))
        );

        //Mock Calls
        when(userMapper.userDtoToUser(dto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.userToUserDtoResponse(savedUser)).thenReturn(dtoResponse);

        //when
        UserDtoResponse response = userService.saveUser(dto);

        //then

        assertEquals(dto.fullName(), response.getFullName());
        assertEquals(dto.CPF(), response.getCPF());
        assertEquals(dto.email(), response.getEmail());
        assertEquals(dto.userType(), response.getUserType());

        assertEquals(1, response.getSentTransactions().size());
        assertEquals(1, response.getReceivedTransactions().size());

        verify(userMapper, times(1)).userDtoToUser(dto);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).userToUserDtoResponse(savedUser);

    }
}
