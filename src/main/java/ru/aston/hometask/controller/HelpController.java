package ru.aston.hometask.controller;

import lombok.AllArgsConstructor;
import ru.aston.hometask.context.AppRequest;
import ru.aston.hometask.utils.Command;

@AllArgsConstructor
public class HelpController implements Controller {

    @Override
    public void execute(AppRequest request) {
        System.out.println(Command.HELP_INFO);
    }
}
