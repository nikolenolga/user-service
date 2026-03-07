package ru.aston.hometask.controller;

import lombok.AllArgsConstructor;
import ru.aston.hometask.context.AppRequest;
import ru.aston.hometask.dto.UserTo;
import ru.aston.hometask.service.UserService;
import ru.aston.hometask.utils.Message;

import java.util.Collection;

@AllArgsConstructor
public class PrintUsersController implements Controller {
    private final UserService userService;

    @Override
    public void execute(AppRequest request) {
        Collection<UserTo> all = userService.getAll();
        if (all.isEmpty()) {
            System.out.println(Message.NO_USERS_FOUND);
        }
        all.forEach(System.out::println);
    }
}
