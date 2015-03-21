package com.rpm.pixelcat.engine.kernel;

public enum KernelStatePropertyEnum {
    ////                   ////
    // READ/WRITE PROPERTIES //
    ////                   ////

    // preferred maximum frame rate
    FRAME_RATE,
    // registered active collection of game object managers
    ACTIVE_GAME_OBJECT_MANAGERS,
    // logging level from log4j.Level
    LOG_LVL,
    // font display
    FONT_DISPLAY_ENABLED,
    // exit signal
    EXIT_SIGNAL,

    ////                  ////
    // READ ONLY PROPERTIES //
    ////                  ////

    // game loop duration in ms
    GAME_LOOP_DURATION_MS,

    ;
}
