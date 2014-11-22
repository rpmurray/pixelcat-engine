package com.rpm.pixelcat.common;

public class Printer {
    public void printError(Throwable error) {
        System.out.print("ERROR: " + error + "\n");
    }

    public void printDebug(String debug) {
        System.out.print("DEBUG: " + debug + "\n");
    }
}
