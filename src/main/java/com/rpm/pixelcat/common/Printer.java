package com.rpm.pixelcat.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Printer {
    private Logger logger;

    public Printer() {
        logger = LoggerFactory.getLogger(Printer.class);
    }

    public void printError(Throwable throwable) {
        logger.error("", throwable);
    }

    public void printDebug(String debug) {
        logger.debug(debug);
    }
}
