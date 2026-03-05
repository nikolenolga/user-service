package ru.aston.hometask.service;

import lombok.AllArgsConstructor;
import ru.aston.hometask.dto.UserTo;
import ru.aston.hometask.entity.User;
import ru.aston.hometask.exception.AppException;
import ru.aston.hometask.mapper.Dto;
import ru.aston.hometask.repository.Repository;

import java.util.Collection;

@AllArgsConstructor
public class UserService {
    private final Repository<User> repository;

    public UserTo create(String name, String email, Integer age) {
        User user = User.builder()
                .name(name).email(email).age(age)
                .build();

        UserTo created = repository.create(user)
                .map(Dto.MAPPER::from)
                .orElseThrow(() -> new AppException("User not created"));

        System.out.printf("User created: %s%n", created);
        return created;
    }

    public UserTo read(Long id) {
        UserTo user = repository.get(id)
                .map(Dto.MAPPER::from)
                .orElseThrow(() -> new AppException("User with id=%d not found".formatted(id)));

        System.out.printf("User found: %s%n", user);
        return user;
    }

    public UserTo update(Long id, String name, String email, Integer age) {
        User user = repository.get(id)
                .orElseThrow(() -> new AppException("User with id=%d not found".formatted(id)));

        if (name != null) {
            user.setName(name);
        }
        if (email != null) {
            user.setEmail(email);
        }
        if (age != null) {
            user.setAge(age);
        }

        UserTo updated = repository.update(user)
                .map(Dto.MAPPER::from)
                .orElseThrow(() -> new AppException("Can't update user: %s".formatted(user)));

        System.out.printf("User updated: %s%n", updated);
        return updated;
    }

    public void delete(Long id) {
        boolean deleted = repository.delete(id);

        System.out.printf("User with id %s %s%n", id, deleted ? "deleted" : "not found");
    }

    public Collection<UserTo> getAll() {
        Collection<UserTo> users = repository.getAll()
                .stream()
                .map(Dto.MAPPER::from)
                .toList();

        System.out.printf("Found %d users%n", users.size());
        return users;
    }
}
