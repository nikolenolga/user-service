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

    public void registerController(String route, Controller controller) {
        controllers.put(route, controller);
    }

    public void send(AppRequest request) throws AppException {
        String route = request.getRoute();

        if (Objects.isNull(route)) {
            throw new AppException(Message.NO_ROUTE_FOUND_SEE_HELP);
        }
        if (!controllers.containsKey(route)) {
            throw new AppException(Message.CAN_NOT_PROCESS_THE_ROUTE_X_SEE_HELP.formatted(route));
        }

        log.debug("Processing the route {}", route);
        controllers.get(route).execute(request);
    }

}
