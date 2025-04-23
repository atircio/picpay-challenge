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
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;




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

    @Test
    void should_return_a_list_of_users(){
        //Given
        List<User> data = new ArrayList<>();
        data.add(new User(
                "John Smith",
                "2323232323232",
                "john1244@gmail.com",
                "Pass1234",
                new BigDecimal("233434"),
                UserType.COMMON,
                List.of(new Transaction()),
                List.of(new Transaction())));

        List<UserDtoResponse> dataDto = new ArrayList<>();
        dataDto.add(new UserDtoResponse(
                "John Smith",
                "2323232323232",
                "john1244@gmail.com",
                new BigDecimal("233434"),
                UserType.COMMON,
                List.of(new TransactionForUsersDto(new BigDecimal(100), "23224435535", TransactionStatus.APPROVED, LocalDateTime.now())),
                List.of(new TransactionForUsersDto(new BigDecimal(100), "23224435535", TransactionStatus.APPROVED, LocalDateTime.now()))));

        //Mock Calls

        when(userRepository.findAll()).thenReturn(data);
        when(userMapper.userToUserDtoResponse(data)).thenReturn(dataDto);

        //When

        List<UserDtoResponse> responses = userService.findAllUsers();

        //Then

        assertEquals(data.size(), responses.size());
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).userToUserDtoResponse(data);




    }

    @Test
    void shouldReturnUserDtoResponse_whenUserIsFoundByCpf(){
        //Given
        String cpf = "2323232323232";

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

        UserDtoResponse dto = new UserDtoResponse(
                "John Smith",
                "2323232323232",
                "john1244@gmail.com",
                new BigDecimal("233434"),
                UserType.COMMON,
                List.of(new TransactionForUsersDto(new BigDecimal(200), "3524242342", TransactionStatus.APPROVED,  LocalDateTime.now())),
                List.of(new TransactionForUsersDto(new BigDecimal(200), "3524242342", TransactionStatus.APPROVED,  LocalDateTime.now()))
        );


        //Mock calls
        when(userRepository.findByCPF(cpf)).thenReturn(Optional.of(user));
        when(userMapper.userToUserDtoResponse(user)).thenReturn(dto);

        //When
        UserDtoResponse response = userService.findUserByCpf(cpf);

        //Then
        assertNotNull(response);
        assertEquals(cpf, response.getCPF());
        assertEquals(user.getFullName(), response.getFullName());
        verify(userRepository, times(1)).findByCPF(cpf);
        verify(userMapper, times(1)).userToUserDtoResponse(user);

    }

    @Test
    void shouldThrowAnException_whenUserNotFoundedByCpf(){
        //Given
        String cpf = "345678977287";
        //Mock calls
        when(userRepository.findByCPF(cpf)).thenReturn(Optional.empty());

        //When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> userService.findUserByCpf(cpf));

        //Then
        assertEquals("User not found with CPF: " + cpf, exception.getMessage());
        verify(userRepository, times(1)).findByCPF(cpf);
        verify(userMapper, never()).userToUserDtoResponse(any(User.class));
    }

    @Test
    void shouldReturnUserDtoResponse_whenUserFoundByEmail(){
        //Given
        String email = "john1244@gmail.com";

        User user = new User();
        user.setEmail(email);

        UserDtoResponse dto = new UserDtoResponse();
        dto.setEmail(email);

        //Mock Calls
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(userMapper.userToUserDtoResponse(user)).thenReturn(dto);

        //When

        UserDtoResponse response = userService.findUserByEmail(email);

        //Then

        assertNotNull(response);
        assertEquals(user.getEmail(), response.getEmail());

        verify(userRepository, times(1)).findByEmail(email);
        verify(userMapper, times(1)).userToUserDtoResponse(user);
    }

    @Test
    void shouldTrowAnException_whenUserNotFoundByEmail(){
        //Given
        String email = "random@something.com";

        //Mock calls
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        //When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> userService.findUserByEmail(email));

        //Then
        assertEquals("User not found with email: " + email, exception.getMessage());
        verify(userRepository, times(1)).findByEmail(email);
        verify(userMapper, never()).userToUserDtoResponse(any(User.class));

    }


}
