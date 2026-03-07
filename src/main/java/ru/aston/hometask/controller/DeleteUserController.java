package ru.aston.hometask.controller;

import lombok.AllArgsConstructor;
import ru.aston.hometask.context.AppRequest;
import ru.aston.hometask.service.UserService;
import ru.aston.hometask.utils.Key;
import ru.aston.hometask.utils.Message;

import java.util.Objects;

@AllArgsConstructor
public class DeleteUserController implements Controller {
    UserService userService;

    @Override
    public void execute(AppRequest request) {
        Long id = request.getLongParameter(Key.ID);

        if (Objects.isNull(id)) {
            System.out.println(Message.COMMAND_REQUIRES_USER_ID_PARAMETER);
            return;
        }

        userService.delete(id);
        System.out.printf(Message.USER_WITH_ID_S_DELETED_N, id);
    }
}
