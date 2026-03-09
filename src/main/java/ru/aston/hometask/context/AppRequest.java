package ru.aston.hometask.context;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import ru.aston.hometask.exception.AppException;
import ru.aston.hometask.utils.Command;
import ru.aston.hometask.utils.Message;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AppRequest {
    private static final String REQUEST_PARTS_SPLITERATOR = " ";
    private static final String PARAMETERS_SPLITERATOR = "=";
    private static final String EXIT_COMMAND = Command.EXIT;
    private static final String HELP_COMMAND = Command.HELP;
    private final Map<String, String> parameters = new HashMap<>();
    @Getter
    private String route;
    @Getter
    private String commandName;

    private AppRequest() {
    }

    public static AppRequest createRequest(String requestLine) {
        log.info("Request: {}", requestLine);
        if (StringUtils.isBlank(requestLine)) {
            throw new AppException(Message.EMPTY_REQUEST);
        }

        int firstParameterIndex = 0;
        String[] requestParts = requestLine.trim().split(REQUEST_PARTS_SPLITERATOR);
        AppRequest request = new AppRequest();
        String commandNamePart = requestParts[0].trim();

        if (!commandNamePart.contains(PARAMETERS_SPLITERATOR) && !StringUtils.isBlank(commandNamePart)) {
            firstParameterIndex = 1;
            request.commandName = commandNamePart.toLowerCase();
        }

        if (requestParts.length > 1) {
            String routePart = requestParts[1].trim();
            if (!routePart.contains(PARAMETERS_SPLITERATOR) && !StringUtils.isBlank(routePart)) {
                firstParameterIndex = 2;
                request.route = routePart.toLowerCase();
            }
        }

        if (requestParts.length > firstParameterIndex) {
            String[] parameters = Arrays.copyOfRange(requestParts, firstParameterIndex, requestParts.length);
            setRequestParameters(parameters, request);
        }
        return request;
    }

    private static void setRequestParameters(String[] requestParts, AppRequest request) {
        for (String part : requestParts) {
            String[] parameterParts = part.trim().split(PARAMETERS_SPLITERATOR);
            if (parameterParts.length > 2) {
                throw new AppException(Message.WRONG_REQUEST_PARAMETER_SYNTAXES_X.formatted(part));
            }

            String parameterKey = parameterParts[0];
            String parameterValue = (parameterParts.length == 2)
                    ? parameterParts[1]
                    : null;
            request.parameters.put(parameterKey, parameterValue);
        }
    }

    public String getParameter(String parameterKey) {
        String parameterValue = parameters.get(parameterKey);
        return parameterValue == null
                ? null
                : parameterValue.trim();
    }

    public String getStringParameterOrThrow(String parameterKey) {
        String parameterValue = getParameter(parameterKey);

        if (StringUtils.isBlank(parameterValue)) {
            throw new AppException(Message.ENTER_PARAMETER_VALUE_X.formatted(parameterKey));
        }

        return parameterValue;
    }

    public Integer getIntegerParameter(String parameterKey) {
        String parameterValue = getParameter(parameterKey);

        try {
            return parameterValue == null
                    ? null
                    : Integer.parseInt(parameterValue);
        } catch (NumberFormatException e) {
            throw new AppException(Message.WRONG_PARAMETER_VALUE_KEY_X_VALUE_X.formatted(parameterKey, parameterValue));
        }
    }

    public Long getLongParameter(String parameterKey) {
        String parameterValue = getParameter(parameterKey);
        try {
            return parameterValue == null
                    ? null
                    : Long.parseLong(parameterValue);
        } catch (NumberFormatException e) {
            throw new AppException(Message.WRONG_PARAMETER_VALUE_KEY_X_VALUE_X.formatted(parameterKey, parameterValue));
        }
    }

    public boolean containsParameter(String parameterKey) {
        return parameters.containsKey(parameterKey);
    }

    public void checkParametersAmount(Integer expectedParametersAmount) {
        if (parameters.size() > expectedParametersAmount) {
            throw new AppException(Message.WRONG_PARAMETERS_AMOUNT);
        }
    }

    public boolean isExitRequest() {
        return EXIT_COMMAND.equals(commandName);
    }

    public boolean isHelpRequest() {
        return HELP_COMMAND.equals(commandName);
    }
}
