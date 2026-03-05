package ru.aston.hometask.service;

import lombok.AllArgsConstructor;
import ru.aston.hometask.entity.User;
import ru.aston.hometask.exception.AppException;
import ru.aston.hometask.repository.Repository;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
public class UserService {
    private final Repository<User> repository;

    public User create(String name, String email, Integer age) {
        User user = User.builder()
                .name(name)
                .email(email)
                .age(age)
                .build();
        User created = repository.create(user).get();

        System.out.printf("User created: %s%n", created);
        return created;
    }

    public User read(Long id) {
        Optional<User> optionalUser = repository.get(id);
        if (optionalUser.isEmpty()) {
            System.out.printf("User with id=%d not found%n", id);
            return null;
        }

        User user = optionalUser.get();
        System.out.printf("User found: %s%n", user);
        return user;
    }

    public User update(Long id, String name, String email, Integer age) {
        Optional<User> optionalUser = repository.get(id);
        if (optionalUser.isEmpty()) {
            throw new AppException("User with id=%d not found".formatted(id));
        }

        User oldUser = optionalUser.get();
        if (name != null) {
            oldUser.setName(name);
        }
        if (email != null) {
            oldUser.setEmail(email);
        }
        if (age != null) {
            oldUser.setAge(age);
        }

        User updated = repository.update(oldUser).get();

        System.out.printf("User updated: %s%n", updated);
        return updated;
    }

    public void delete(Long id) {
        boolean deleted = repository.delete(id);

        System.out.printf("User with id %s %s%n", id, deleted ? "deleted" : "not found");
    }

    public Collection<User> getAll() {
        Collection<User> users = repository.getAll();
        System.out.printf("Found %d users%n", users.size());
        return users;
    }
}
