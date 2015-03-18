package com.rpm.pixelcat.common;

import org.apache.log4j.*;


public class Printer {
    private Logger logger;

    public Printer(Class context) {
        logger = Logger.getLogger(context);
        logger.addAppender(new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));
        logger.setAdditivity(false);
    }

    public void printError(Throwable throwable) {
        logger.error("", throwable);
    }

    public void printWarning(String warning) {
        logger.warn(warning);
    }

    public void printInfo(String info) {
        logger.info(info);
    }

    public void printDebug(String debug) {
        logger.debug(debug);
    }

    public void printTrace(String trace) {
        logger.trace(trace);
    }

    public static void setLevel(Level level) {
        System.out.print("--- [main] MESSAGE " + Printer.class.getName() + "  - Log level changed to " + level + "\n");
        org.apache.log4j.Logger.getRootLogger().setLevel(level);
    }
}
