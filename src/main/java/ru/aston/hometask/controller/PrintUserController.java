package ru.aston.hometask.controller;

import lombok.AllArgsConstructor;
import ru.aston.hometask.context.AppData;
import ru.aston.hometask.context.AppRequest;
import ru.aston.hometask.entity.User;
import ru.aston.hometask.service.UserService;
import ru.aston.hometask.utils.Command;
import ru.aston.hometask.utils.Key;
import ru.aston.hometask.utils.Message;

import java.util.Objects;

@AllArgsConstructor
public class PrintUserController implements Controller {
    private final UserService userService;

    @Override
    public void execute(AppRequest request, AppData appData) {
        if (request.containsParameter(Command.ALL)) {
            userService.getAll().forEach(System.out::println);
            return;
        }

        User user = appData.getAttribute(Key.USER, User.class);
        System.out.println(Objects.nonNull(user)
                ? user
                : Message.NO_LAST_REQUESTED_USER_SAVED);

    }
}
