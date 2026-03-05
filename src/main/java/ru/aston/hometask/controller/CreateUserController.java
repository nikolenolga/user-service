package ru.aston.hometask.controller;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import ru.aston.hometask.context.AppData;
import ru.aston.hometask.context.AppRequest;
import ru.aston.hometask.dto.UserTo;
import ru.aston.hometask.service.UserService;
import ru.aston.hometask.utils.Key;
import ru.aston.hometask.utils.Message;

@AllArgsConstructor
public class CreateUserController implements Controller {
    private UserService userService;

    @Override
    public void execute(AppRequest request, AppData appData) {
        String name = request.getParameter(Key.NAME);
        String email = request.getParameter(Key.EMAIL);
        Integer age = request.getIntegerParameter(Key.AGE);

        if (ObjectUtils.anyNull(name, email, age)) {
            System.out.println(Message.ENTER_ALL_FIELD_VALUES_TO_CREATE_USER_EXAMPLE);
            return;
        }

        UserTo user = userService.create(name, email, age);
        appData.setAttribute(Key.USER, user);
    }
}
