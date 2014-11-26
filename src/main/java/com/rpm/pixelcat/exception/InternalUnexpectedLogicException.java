package com.rpm.pixelcat.exception;

public class InternalUnexpectedLogicException extends GameException {
    public InternalUnexpectedLogicException(GameErrorCode errorCode) {
        super(errorCode);
    }
}
