package ru.aston.hometask.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.aston.hometask.context.AppRequest;
import ru.aston.hometask.exception.AppException;
import ru.aston.hometask.utils.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class DispatcherController {
    private final Map<String, Controller> controllers = new HashMap<>();

    public void registerController(String command, Controller controller) {
        controllers.put(command, controller);
    }

    public void send(AppRequest request) throws AppException {
        String command = request.getCommandName();

        if (Objects.isNull(command)) {
            throw new AppException(Message.NO_COMMAND_FOUND_SEE_HELP);
        }
        if (!controllers.containsKey(command)) {
            throw new AppException(Message.X_IS_NOT_AN_APP_COMMAND_SEE_HELP.formatted(command));
        }

        log.debug("Executing command {}", command);
        controllers.get(command).execute(request);
    }

}
