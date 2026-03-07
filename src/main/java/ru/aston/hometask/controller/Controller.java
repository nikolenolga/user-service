package ru.aston.hometask.controller;

import ru.aston.hometask.context.AppRequest;

public interface Controller {

    void execute(AppRequest request);

}
