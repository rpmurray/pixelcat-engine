package com.rpm.pixelcat.engine.logic;

public class LogicHandlerFactory {
    private static LogicHandlerFactory instance;

    public static LogicHandlerFactory getInstance() {
        if (instance == null) {
            instance = new LogicHandlerFactory();
        }

        return instance;
    }

    public LogicHandler createLogicHandler() {
        LogicHandler logicHandler = new LogicHandlerImpl();

        return logicHandler;
    }
}
