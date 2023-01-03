package az.mamishov.controller;

import az.mamishov.dto.UserRequest;
import az.mamishov.dto.UserResponse;
import az.mamishov.mapper.UserMapper;
import az.mamishov.model.UserEntity;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id) {
        var user = userMapper.findById(id);
        return ResponseEntity.ok(
                UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .isActive(user.getIsActive())
                        .roles(user.getRoles())
                        .build()
        );
    }

    @PostMapping("/user")
    public ResponseEntity<Object> registerUser(@RequestBody UserRequest userRequest) {
        final String encodedPassword = passwordEncoder.encode(userRequest.getPassword());

        UserEntity user = UserEntity.builder()
                .username(userRequest.getUsername())
                .password(encodedPassword)
                .roles(userRequest.getRoles())
                .isActive(userRequest.getIsActive())
                .build();

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
