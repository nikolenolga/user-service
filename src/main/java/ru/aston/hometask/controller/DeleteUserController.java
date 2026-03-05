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
public class DeleteUserController implements Controller {
    UserService userService;

    @Override
    public void execute(AppRequest request, AppData appData) {
        Long savedId = null;
        User user = appData.getAttribute(Key.USER, User.class);
        if (user != null) {
            savedId = user.getId();
        }

        Long id = request.containsParameter(Key.ID)
                ? request.getLongParameter(Key.ID)
                : savedId;

        if (Objects.isNull(id)) {
            System.out.println(Message.ENTER_USER_ID_EXAMPLE);
            return;
        }

        userService.delete(id);
        if (savedId != null && savedId.equals(id)) {
            appData.removeAttribute(Key.USER);
        }
    }
}
