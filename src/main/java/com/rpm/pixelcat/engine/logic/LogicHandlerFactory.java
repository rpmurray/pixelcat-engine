package com.rpm.pixelcat.engine.logic;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.kernel.KernelState;

import java.io.IOException;
import java.net.URISyntaxException;

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
