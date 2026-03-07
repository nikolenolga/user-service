package ru.aston.hometask.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Message {
    public static final String EMPTY_REQUEST = "Empty request";
    public static final String WRONG_REQUEST_PARAMETER_SYNTAXES_X = "Wrong request parameter syntaxes: %s";
    public static final String ENTER_PARAMETER_VALUE_X = "Enter parameter value %s";
    public static final String WRONG_PARAMETER_VALUE_KEY_X_VALUE_X = "Wrong parameter value: %s=%s";
    public static final String WRONG_PARAMETERS_AMOUNT = "Wrong parameters amount";
    public static final String APPLICATION_TERMINATED_WITH_ERROR_X_N = "The application terminated with an error: %s.";
    public static final String X_IS_NOT_AN_APP_COMMAND_SEE_HELP = "%s is not an app command. See 'help'.";
    public static final String NO_COMMAND_FOUND_SEE_HELP = "No command found. See 'help'.";
    public static final String COMMAND_REQUIRES_USER_ID_PARAMETER = "Command requires user id parameter: id=<value>";
    public static final String COMMAND_REQUIRES_AT_LIST_ONE_FIELD_PARAMETER_FOR_UPDATE_EXAMPLE = "Command requires at list one parameter for update: name=<value> email=<value> age=<value>";
    public static final String COMMAND_REQUIRES_ALL_FIELD_VALUES_TO_CREATE_USER_EXAMPLE = "Command requires all field values to create user: name=<value> email=<value> age=<value>";
    public static final String THE_DATABASE_CONNECTION_PROPERTIES_NOT_FOUND = "The database connection properties not found.";
    public static final String THE_DATABASE_SERVICE_IS_NOT_RESPONDING = "The database service is not responding. Check if the database server is running. Check the correctness of the login, password, and database name in the config.";
    public static final String DRIVER_NOT_FOUND = "Driver not found.";
    public static final String USER_WITH_ID_D_NOT_FOUND = "User with id=%d not found";
    public static final String CAN_T_UPDATE_USER_S = "Can't update user: %s";
    public static final String USER_CREATED_S_N = "User created: %s%n";
    public static final String USER_UPDATED_S_N = "User updated: %s%n";
    public static final String USER_FOUND_S_N = "User found: %s%n";
    public static final String NO_USERS_FOUND = "No users found";
    public static final String USER_WITH_ID_S_DELETED_N = "User with id=%s deleted %n";
    public static final String USER_NOT_CREATED = "User not created";
    public static final String NAME_SHOULD_NOT_BE_BLANK_AND_NOT_GREATER_THAN_50_CHARACTERS = "Name should not be blank and not greater than 50 characters.";
    public static final String INVALID_EMAIL_ADDRESS = "Invalid email address";
    public static final String AGE_SHOULD_BE_BETWEEN_D_AND_D = "Age should be between %d and %d";

    public static final String GREETINGS = """
            === User Service ===
            Available commands:
            %s
            Enter the command:""".formatted(Command.HELP_INFO);
}
