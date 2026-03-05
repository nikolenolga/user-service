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
            help \t\t print commands help info
            print \t\t print last read/created/updated user
            print all \t prints all saved users
            create \t\t creates user
            \t\t\t requires 3 parameters: name=value email=value age=value
            read \t\t reads user with specified id
            \t\t\t requires 1 parameter: id=1
            update \t\t updates user with specified id
            \t\t\t requires 1 parameter: id=1
            \t\t\t requires at list 1 parameter with updating data
            \t\t\t accepts 3 parameters: name=value email=value age=value
            update \t\t updates user with specified id
            delete \t\t delete user with specified id
            \t\t\t requires 1 parameter: id=1
            """;
}
