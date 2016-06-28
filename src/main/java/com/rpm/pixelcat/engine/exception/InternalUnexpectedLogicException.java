package com.rpm.pixelcat.engine.exception;

public class InternalUnexpectedLogicException extends TransientGameException {
    public InternalUnexpectedLogicException(GameErrorCode errorCode) {
        super(errorCode);
    }
}
