package com.anikulin.manager.service;

import com.anikulin.manager.entity.Role;
import com.anikulin.manager.entity.User;
import com.anikulin.manager.repository.UserRepository;
import com.anikulin.manager.util.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private static final String USERNAME_NOT_FOUND_MESSAGE = "User with username %s not exists";
    private static final String USERID_NOT_FOUND_MESSAGE ="User with id %s doesnt exists";

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String username) {

        return userRepository.findByUsername(username).orElseThrow(() ->
                new UserNotFoundException(String.format(USERNAME_NOT_FOUND_MESSAGE, username)));
    }

    public User findUserByUsernameSnippet(String usernameSnippet) {

        return userRepository.findAll().stream()
                .filter(user -> user.getUsername().contains(usernameSnippet))
                .findFirst()
                .orElseThrow(() ->
                        new UserNotFoundException(String.format(USERNAME_NOT_FOUND_MESSAGE, usernameSnippet)));
    }

    public List<User> findAllUsers() {

        return userRepository.findAll();
    }

    @Transactional
    public void setOperatorRole(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String.format(USERID_NOT_FOUND_MESSAGE, userId)));
        user.getRoles().add(Role.OPERATOR);

        userRepository.save(user);
    }
}
