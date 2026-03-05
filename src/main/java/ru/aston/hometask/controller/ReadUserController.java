package ru.aston.hometask.controller;

import lombok.AllArgsConstructor;
import ru.aston.hometask.context.AppData;
import ru.aston.hometask.context.AppRequest;
import ru.aston.hometask.entity.User;
import ru.aston.hometask.service.UserService;
import ru.aston.hometask.utils.Key;
import ru.aston.hometask.utils.Message;

import java.util.Objects;

@AllArgsConstructor
public class ReadUserController implements Controller {
    private final UserService userService;

    @Override
    public void execute(AppRequest request, AppData appData) {
        Long id = request.containsParameter(Key.ID)
                ? request.getLongParameter(Key.ID)
                : null;

        if (Objects.isNull(id)) {
            System.out.println(Message.ENTER_USER_ID_EXAMPLE);
            return;
        }

        User user = userService.read(id);
        appData.setAttribute(Key.USER, user);
    }
}
