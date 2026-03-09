package ru.aston.hometask.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import ru.aston.hometask.context.AppRequest;
import ru.aston.hometask.dto.UserTo;
import ru.aston.hometask.exception.AppException;
import ru.aston.hometask.service.UserService;
import ru.aston.hometask.utils.Command;
import ru.aston.hometask.utils.Key;
import ru.aston.hometask.utils.Message;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
public class UserController implements Controller {
    private final Map<String, Consumer<AppRequest>> operationMap = new HashMap<>();
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
        operationMap.put(Command.CREATE, this::create);
        operationMap.put(Command.READ, this::read);
        operationMap.put(Command.UPDATE, this::update);
        operationMap.put(Command.DELETE, this::delete);
        operationMap.put(Command.PRINT_ALL, this::print);
    }

    @Override
    public void execute(AppRequest request) {
        String command = request.getCommandName();

        if (Objects.isNull(command)) {
            throw new AppException(Message.NO_COMMAND_FOUND_SEE_HELP);
        }
        if (!operationMap.containsKey(command)) {
            throw new AppException(Message.X_IS_NOT_AN_APP_COMMAND_SEE_HELP.formatted(command));
        }

        log.debug("Executing command {}", command);
        operationMap.get(command).accept(request);
    }

    public void create(AppRequest request) {
        String name = request.getParameter(Key.NAME);
        String email = request.getParameter(Key.EMAIL);
        Integer age = request.getIntegerParameter(Key.AGE);

        if (ObjectUtils.anyNull(name, email, age)) {
            System.out.println(Message.COMMAND_REQUIRES_ALL_FIELD_VALUES_TO_CREATE_USER_EXAMPLE);
            return;
        }

        UserTo user = userService.create(name, email, age);
        System.out.printf(Message.USER_CREATED_S_N, user);
    }

    public void read(AppRequest request) {
        Long id = request.getLongParameter(Key.ID);

        if (Objects.isNull(id)) {
            System.out.println(Message.COMMAND_REQUIRES_USER_ID_PARAMETER);
            return;
        }

        UserTo user = userService.read(id);
        System.out.printf(Message.USER_FOUND_S_N, user);
    }

    public void update(AppRequest request) {
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

    public void delete(AppRequest request) {
        Long id = request.getLongParameter(Key.ID);

        if (Objects.isNull(id)) {
            System.out.println(Message.COMMAND_REQUIRES_USER_ID_PARAMETER);
            return;
        }

        userService.delete(id);
        System.out.printf(Message.USER_WITH_ID_S_DELETED_N, id);
    }

    public void print(AppRequest request) {
        Collection<UserTo> all = userService.getAll();
        if (all.isEmpty()) {
            System.out.println(Message.NO_USERS_FOUND);
        }
        all.forEach(System.out::println);
    }

}
