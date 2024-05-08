package com.anikulin.manager.util.converter;

import com.anikulin.manager.dto.UserDto;
import com.anikulin.manager.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public static UserDto convertUserToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();
    }
}
