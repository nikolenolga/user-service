package ru.aston.hometask.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Message {
    public static final String EMPTY_REQUEST = "Empty request";
    public static final String WRONG_REQUEST_PARAMETER_SYNTAXES_X = "Wrong request parameter syntaxes: %s";
    public static final String ENTER_PARAMETER_X = "Enter parameter %s";
    public static final String ENTER_PARAMETER_VALUE_X = "Enter parameter value %s";
    public static final String WRONG_PARAMETER_VALUE_KEY_X_VALUE_X = "Wrong parameter value: %s=%s";
    public static final String WRONG_PARAMETERS_AMOUNT = "Wrong parameters amount";
    public static final String APPLICATION_TERMINATED_WITH_ERROR = "The application terminated with an error.";

    public static final String NO_LAST_REQUESTED_USER_SAVED = "No last requested user saved";
    public static final String X_IS_NOT_AN_APP_COMMAND_SEE_HELP = "%s is not an app command. See 'help'.";
    public static final String NO_COMMAND_FOUND_SEE_HELP = "No command found. See 'help'.";

    public static final String ENTER_USER_ID_EXAMPLE = "Enter user id: id=value";
    public static final String ENTER_AT_LIST_ONE_FIELD_FOR_UPDATE_EXAMPLE = "Enter at list one field for update: name=value email=value age=value";
    public static final String ENTER_ALL_FIELD_VALUES_TO_CREATE_USER_EXAMPLE = "Enter all field values to create user: name=value email=value age=value";

    public static final String CURRENT_COMMAND_X_CURRENT_USER_X_N = "-->> Current command: %s, current user: %s%n";
    public static final String NONE = "none";
    public static final String GREETINGS = """
            === User Service ===
            Available commands:
            %s
            Enter the command:
            """.formatted(Command.HELP_INFO);
}
