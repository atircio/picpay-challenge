package com.atircio.pickpay.resources;

import com.atircio.pickpay.dtos.UserDto;
import com.atircio.pickpay.dtos.UserDtoResponse;
import com.atircio.pickpay.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDtoResponse> save(@RequestBody UserDto dto){
        UserDtoResponse dtoResponse = userService.saveUser(dto);
        return ResponseEntity.ok().body(dtoResponse);
    }
}
