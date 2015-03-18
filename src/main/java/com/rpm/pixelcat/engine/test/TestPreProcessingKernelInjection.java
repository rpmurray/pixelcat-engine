package com.rpm.pixelcat.engine.test;

import com.rpm.pixelcat.engine.common.Printer;
import com.rpm.pixelcat.engine.common.PrinterFactory;
import com.rpm.pixelcat.engine.kernel.KernelInjection;
import com.rpm.pixelcat.engine.kernel.KernelState;

public class TestPreProcessingKernelInjection implements KernelInjection {
    private static Printer PRINTER = PrinterFactory.getInstance().createPrinter(TestPreProcessingKernelInjection.class);

    public void run(KernelState kernelState) {
        PRINTER.printDebug("Test kernel-injected pre-processor executed...");
    }
}
