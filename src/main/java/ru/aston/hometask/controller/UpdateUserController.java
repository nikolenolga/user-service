package ru.aston.hometask.controller;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
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
    public void execute(AppRequest request) {
        Long id = request.getLongParameter(Key.ID);
        String name = request.getParameter(Key.NAME);
        String email = request.getParameter(Key.EMAIL);
        Integer age = request.getIntegerParameter(Key.AGE);

        if (Objects.isNull(id)) {
            System.out.println(Message.COMMAND_REQUIRES_USER_ID_PARAMETER);
            return;
        }
        if (ObjectUtils.allNull(name, email, age)) {
            System.out.println(Message.COMMAND_REQUIRES_AT_LIST_ONE_FIELD_PARAMETER_FOR_UPDATE_EXAMPLE);
            return;
        }

        UserTo updated = userService.update(id, name, email, age);
        System.out.printf(Message.USER_UPDATED_S_N, updated);
    }
}
