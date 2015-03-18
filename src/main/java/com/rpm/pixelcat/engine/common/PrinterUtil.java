package com.rpm.pixelcat.engine.common;

import org.apache.log4j.Level;

class PrinterUtil {
    static void setLevel(Level level) {
        System.out.print("--- [main] MESSAGE " + PrinterImpl.class.getName() + "  - Log level changed to " + level + "\n");
        org.apache.log4j.Logger.getRootLogger().setLevel(level);
    }
}
