package info.masterfrog.pixelcat.engine.exception;

public enum GameErrorCode {
    UNSUPPORTED_RESOURCE_FOR_RENDERING(20001L, "Found resource does not match a supported renderable entity and will be skipped."),

    LOGIC_ERROR(30999L, "Unspecified logic error during game logic processing."),
    PHYSICS_COLLISION_LOGIC_ERROR(31199L, "Unspecified logic error in physics collision system."),

    GAME_CLOCK_TAG_NOT_EXISTS(39101L, "The referenced tag does not exist for this game clock."),
    GAME_CLOCK_ERROR(39199L, "Unspecified game clock error."),

    HID_EVENT_TYPE_UNSUPPORTED(40001L, "Supplied hid event type not supported."),
    HID_EVENT_UNSUPPORTED(40002L,"Supplied hid event not supported."),

    KERNEL_INITIALIZATION_FAILED(50001L,"Kernel initialization failed."),
    KERNEL_ACTION_UNSUPPORTED(50002L,"Supplied kernel action not supported."),
    KERNEL_LOOP_SLEEP_FAILED(50003L,"Kernel loop sleep failed."),
    KERNEL_INJECTION_ERROR(50011L, "Kernel injection processing encountered an error."),

    GRAPHICS_ENGINE_ERROR(71001L,"Unspecified graphics engine error."),

    SOUND_ENGINE_ERROR(72001L,"Unspecified sound engine error."),

    XML_PARSER_ERROR(81001L, "XML file parsing failed."),

    EXIT_CONDITION(990001L, "Game exiting"),
    TERMINAL_ERROR_CONDITION(99997L, "Terminal error occurred, program execution aborted."),
    UNSUPPORTED_FUNCTIONALITY(99998L, "Functionality that is attempting to be executed is incomplete or unsupported."),
    INTERNAL_ERROR(99999L, "Some undefined internal error occurred."),
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
