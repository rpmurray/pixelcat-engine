package com.rpm.pixelcat.exception;

public class ExitException extends GameException {
    public ExitException() {
        super(GameErrorCode.EXIT_CONDITION);
    }
}
