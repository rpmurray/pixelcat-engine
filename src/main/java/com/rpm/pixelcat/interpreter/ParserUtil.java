package com.rpm.pixelcat.interpreter;

import com.rpm.pixelcat.engine.common.printer.Printer;
import com.rpm.pixelcat.engine.common.printer.PrinterFactory;

public class ParserUtil {
    private static Printer printer = PrinterFactory.getInstance().createPrinter(Parser.class);

    public static Printer getPrinter() {
        return printer;
    }
}
