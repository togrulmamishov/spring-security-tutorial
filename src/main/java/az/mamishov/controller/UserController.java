package az.mamishov.controller;

import az.mamishov.dto.UserRequest;
import az.mamishov.dto.UserResponse;
import az.mamishov.mapper.UserMapper;
import az.mamishov.mapstruct.UserDtoMapper;
import az.mamishov.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private final UserDtoMapper mapper;

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id) {
        var user = userMapper.findById(id);
        var response = mapper.userEntityToResponse(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user")
    public ResponseEntity<Object> registerUser(@RequestBody UserRequest userRequest) {
        final String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
        UserEntity user = mapper.userRequestToEntity(userRequest);
        user.setPassword(encodedPassword);
        userMapper.insertUser(user);
        Integer id = userMapper.getInsertedId();
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return new ResponseEntity<>(uri, HttpStatus.CREATED);
    }
}
