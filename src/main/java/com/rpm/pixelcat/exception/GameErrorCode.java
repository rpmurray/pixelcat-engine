package com.rpm.pixelcat.exception;

public enum GameErrorCode {
    UNSUPPORTED_RESOURCE_FOR_RENDERING(20001L, "Found resource does not match a supported renderable entity and will be skipped."),

    LOGIC_ERROR(30001L, "Unspecified logic error during game logic processing."),

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
