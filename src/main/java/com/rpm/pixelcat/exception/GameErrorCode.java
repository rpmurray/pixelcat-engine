package com.rpm.pixelcat.exception;

public enum GameErrorCode {
    GAME_LOOP_TIME_EXCEEDED(10001L, "The game loop execution time exceeded the alloted time window for the current configured FPS rating."),

    HID_KEYBOARD_EVENT_TYPE_UNSUPPORTED(40001L, "Supplied keyboard event type not supported."),
    HID_KEYBOARD_EVENT_UNSUPPORTED(40002L,"Supplied keyboard event not supported."),

    EXIT_CONDITION(990001L, "Game exiting"),
    INTERNAL_ERROR(99999L, "Some undefined internal error occured"),
    ;

    private Long code;
    private String message;

    GameErrorCode(Long code, String message) {
        this.code = code;
        this.message = message;
    }

    public Long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "GameErrorCode{" +
            "code=" + code +
            ", message='" + message + '\'' +
            '}';
    }
}
