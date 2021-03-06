package info.masterfrog.pixelcat.engine.kernel;

public enum KernelStateProperty {
    ////                   ////
    // READ/WRITE PROPERTIES //
    ////                   ////

    // bounds bounds
    SCREEN_BOUNDS,
    // preferred maximum frame rate
    FRAME_RATE,
    // background color
    BACKGROUND_COLOR,
    // registered active collection of game object managers
    ACTIVE_GAME_OBJECT_MANAGERS,
    // logging level from log4j.Level
    LOG_LVL,
    // font display
    FONT_DISPLAY_ENABLED,
    // mouse position
    MOUSE_POSITION,
    // hid event bindings
    HID_EVENT_BINDER,
    // kernel action bindings
    KERNEL_ACTION_BINDER,

    ////                  ////
    // READ ONLY PROPERTIES //
    ////                  ////

    // game loop duration in ms
    GAME_LOOP_DURATION_NS,
    // canvases for rendering
    CANVAS_MANAGER,

    ;
}
