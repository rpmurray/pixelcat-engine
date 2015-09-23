package com.rpm.pixelcat.engine.exception;

public enum GameErrorCode {
    UNSUPPORTED_RESOURCE_FOR_RENDERING(20001L, "Found resource does not match a supported renderable entity and will be skipped."),

    LOGIC_ERROR(30001L, "Unspecified logic error during game logic processing."),
    GAME_CLOCK_ERROR(39101L, "Unspecified game clock error."),

    HID_EVENT_TYPE_UNSUPPORTED(40001L, "Supplied hid event type not supported."),
    HID_EVENT_UNSUPPORTED(40002L,"Supplied hid event not supported."),

    KERNEL_ACTION_UNSUPPORTED(50001L,"Supplied kernel action not supported."),

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
