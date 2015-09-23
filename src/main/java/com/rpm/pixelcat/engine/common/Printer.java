package com.rpm.pixelcat.engine.common;

import org.apache.log4j.Level;

public interface Printer {
    public void printError(Throwable throwable);

    public void printWarning(String warning);

    public void printInfo(String info);

    public void printDebug(String debug);

    public void printTrace(String trace);

    public static void setLevel(Level level) {
        PrinterUtil.setLevel(level);
    }

    public static Class<Level> getLogLevelClass() {
        return PrinterUtil.getLogLevelClass();
    }

    public static Level getLogLevelFatal() {
        return PrinterUtil.getLogLevelFatal();
    }

    public static Level getLogLevelError() {
        return PrinterUtil.getLogLevelError();
    }

    public static Level getLogLevelWarn() {
        return PrinterUtil.getLogLevelWarn();
    }

    public static Level getLogLevelInfo() {
        return PrinterUtil.getLogLevelInfo();
    }

    public static Level getLogLevelDebug() {
        return PrinterUtil.getLogLevelDebug();
    }

    public static Level getLogLevelTrace() {
        return PrinterUtil.getLogLevelTrace();
    }
}
