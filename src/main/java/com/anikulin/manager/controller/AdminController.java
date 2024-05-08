package com.anikulin.manager.controller;

import com.anikulin.manager.dto.UserDto;
import com.anikulin.manager.service.UserService;
import com.anikulin.manager.util.converter.UserConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getUserList() {
        List<UserDto> users = userService.findAllUsers().stream()
                .map(UserConverter::convertUserToDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> findUser(@PathVariable String username) {
        UserDto user = UserConverter.convertUserToDto(userService.findUserByUsernameSnippet(username));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PatchMapping("/user/role/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> setOperatorRole(@PathVariable Long id) {
        userService.setOperatorRole(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
