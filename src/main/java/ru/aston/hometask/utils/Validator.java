package ru.aston.hometask.utils;

import org.apache.commons.lang3.StringUtils;
import ru.aston.hometask.dto.UserTo;
import ru.aston.hometask.exception.AppException;

import java.util.StringJoiner;

public class Validator {
    public static final Integer MIN_AGE = 0;
    public static final Integer MAX_AGE = 150;
    public static final int MAX_NAME_LENGTH = 50;
    private static final String EMAIL_MATCHER = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    public static void validate(UserTo user) {
        validateName(user.getName());
        validateEmail(user.getEmail());
        validateAge(user.getAge());
    }

    public static void validate(String name, String email, Integer age) {
        boolean isValid = true;
        StringJoiner messageJoiner = new StringJoiner("\n");
        if (isNotValidName(name)) {
            messageJoiner.add(Message.NAME_SHOULD_NOT_BE_BLANK_AND_NOT_GREATER_THAN_50_CHARACTERS);
            isValid = false;
        }
        if (isNotValidEmail(email)) {
            messageJoiner.add(Message.INVALID_EMAIL_ADDRESS);
            isValid = false;
        }
        if (isNotValidAge(age)) {
            messageJoiner.add(Message.AGE_SHOULD_BE_BETWEEN_D_AND_D.formatted(MIN_AGE, MAX_AGE));
            isValid = false;
        }

        if (!isValid) {
            throw new AppException(messageJoiner.toString());
        }
    }

    public static void validateName(String name) {
        if (isNotValidName(name)) {
            throw new AppException(Message.NAME_SHOULD_NOT_BE_BLANK_AND_NOT_GREATER_THAN_50_CHARACTERS);
        }
    }

    public static void validateEmail(String email) {
        if (isNotValidEmail(email)) {
            throw new AppException(Message.INVALID_EMAIL_ADDRESS);
        }
    }

    public static void validateAge(int age) {
        if (isNotValidAge(age)) {
            throw new AppException(Message.AGE_SHOULD_BE_BETWEEN_D_AND_D.formatted(MIN_AGE, MAX_AGE));
        }
    }

    private static boolean isNotValidName(String name) {
        return StringUtils.isBlank(name) || name.length() > MAX_NAME_LENGTH;
    }

    private static boolean isNotValidEmail(String email) {
        return !email.matches(EMAIL_MATCHER);
    }

    private static boolean isNotValidAge(int age) {
        return age < MIN_AGE || age > MAX_AGE;
    }
}
