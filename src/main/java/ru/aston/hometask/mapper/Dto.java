package ru.aston.hometask.mapper;

import ru.aston.hometask.dto.UserTo;
import ru.aston.hometask.entity.User;

public class Dto {
    public static final Dto MAPPER = new Dto();

    public User from(UserTo userTo) {
        return User.builder()
                .id(userTo.getId())
                .name(userTo.getName())
                .email(userTo.getEmail())
                .createdAt(userTo.getCreatedAt())
                .build();
    }

    public UserTo from(User user) {
        return UserTo.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
