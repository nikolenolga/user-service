package ru.aston.hometask.service;

import ru.aston.hometask.dto.UserTo;

public interface UserService extends Service<UserTo> {

    UserTo create(String name, String email, Integer age);

    UserTo update(Long id, String name, String email, Integer age);
}
