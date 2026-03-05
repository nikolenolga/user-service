package ru.aston.hometask.controller;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import ru.aston.hometask.context.AppData;
import ru.aston.hometask.context.AppRequest;
import ru.aston.hometask.dto.UserTo;
import ru.aston.hometask.service.UserService;
import ru.aston.hometask.utils.Key;
import ru.aston.hometask.utils.Message;

import java.util.Objects;

@AllArgsConstructor
public class UpdateUserController implements Controller {
    private UserService userService;

    @Override
    public void execute(AppRequest request, AppData appData) {
        UserTo user = appData.getAttribute(Key.USER, UserTo.class);
        Long id = request.getLongParameter(Key.ID);

        if (Objects.nonNull(id)) {
            appData.removeAttribute(Key.USER);
        } else if (Objects.isNull(user)) {
            System.out.println(Message.ENTER_USER_ID_EXAMPLE);
            return;
        } else {
            id = user.getId();
        }

        String name = request.getParameter(Key.NAME);
        String email = request.getParameter(Key.EMAIL);
        Integer age = request.getIntegerParameter(Key.AGE);

        if (ObjectUtils.allNull(name, email, age)) {
            System.out.println(Message.ENTER_AT_LIST_ONE_FIELD_FOR_UPDATE_EXAMPLE);
            return;
        }

        UserTo updated = userService.update(id, name, email, age);
        appData.setAttribute(Key.USER, updated);
    }
}
