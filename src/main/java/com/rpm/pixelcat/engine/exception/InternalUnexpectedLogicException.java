package com.rpm.pixelcat.engine.exception;

public class InternalUnexpectedLogicException extends GameException {
    public InternalUnexpectedLogicException(GameErrorCode errorCode) {
        super(errorCode);
    }
}
