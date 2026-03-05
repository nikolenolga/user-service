package ru.aston.hometask.controller;

import ru.aston.hometask.context.AppData;
import ru.aston.hometask.context.AppRequest;

public interface Controller {

    void execute(AppRequest request, AppData appData);
}
