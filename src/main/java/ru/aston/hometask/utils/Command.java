package ru.aston.hometask.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Command {
    public static final String CREATE = "create";
    public static final String READ = "read";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String EXIT = "exit";
    public static final String HELP = "help";
    public static final String PRINT = "print";
    public static final String ALL = "all";

    public static final String HELP_INFO = """
            help
            \t\t print commands help info
            print
            \t\t prints all saved users
            create name=<value> email=<value> age=<value>
            \t\t creates user
            \t\t requires all 3 parameters
            read id=<value>
            \t\t reads user with specified id
            \t\t requires 1 parameter: id=<value>
            update id=<value> name=<value> email=<value> age=<value>
            \t\t updates user with specified id
            \t\t requires parameter: id=<value>
            \t\t requires at list 1 parameter with updating data: name=<value> email=<value> age=<value>
            delete id=<value>
            \t\t delete user with specified id
            \t\t requires 1 parameter: id=<value>
            """;
}
