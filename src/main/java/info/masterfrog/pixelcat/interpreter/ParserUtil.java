package info.masterfrog.pixelcat.interpreter;

import info.masterfrog.pixelcat.engine.common.printer.Printer;
import info.masterfrog.pixelcat.engine.common.printer.PrinterFactory;

public class ParserUtil {
    private static Printer printer = PrinterFactory.getInstance().createPrinter(Parser.class);

    public static Printer getPrinter() {
        return printer;
    }
}
