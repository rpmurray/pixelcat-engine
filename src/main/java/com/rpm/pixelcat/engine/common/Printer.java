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
}
