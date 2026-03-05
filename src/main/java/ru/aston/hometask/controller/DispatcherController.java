package ru.aston.hometask.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.aston.hometask.context.AppData;
import ru.aston.hometask.context.AppRequest;
import ru.aston.hometask.exception.AppException;
import ru.aston.hometask.utils.Key;
import ru.aston.hometask.utils.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
public class DispatcherController {
    private final Map<String, Controller> controllers = new HashMap<>();
    @Getter
    private AppData appData;

    public void registerController(String command, Controller controller) {
        controllers.put(command, controller);
    }

    public void send(AppRequest request) throws AppException {
        String requestCommandName = request.getCommandName();
        String lastCommandName = appData.getAttribute(Key.COMMAND, String.class);

        String command = Objects.isNull(requestCommandName)
                ? lastCommandName
                : requestCommandName;

        if (Objects.isNull(command)) {
            throw new AppException(Message.NO_COMMAND_FOUND_SEE_HELP);
        }

        if (!controllers.containsKey(command)) {
            throw new AppException(Message.X_IS_NOT_AN_APP_COMMAND_SEE_HELP.formatted(command));
        }

        if (!command.equals(lastCommandName)) {
            appData.setAttribute(Key.COMMAND, command);
        }

        controllers.get(command).execute(request, appData);
    }

}
