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
                .age(userTo.getAge())
                .createdAt(userTo.getCreatedAt())
                .build();
    }

    public UserTo from(User user) {
        return new UserTo(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                user.getCreatedAt());
    }
}
