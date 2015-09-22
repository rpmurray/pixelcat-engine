package com.rpm.pixelcat.engine.test;

import com.rpm.pixelcat.engine.common.MemoryMonitorFactory;
import com.rpm.pixelcat.engine.common.Printer;
import com.rpm.pixelcat.engine.common.PrinterFactory;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.kernel.KernelInjection;
import com.rpm.pixelcat.engine.kernel.KernelState;

public class PostRenderingKernelInjection implements KernelInjection {
    private static final Printer PRINTER = PrinterFactory.getInstance().createPrinter(PostRenderingKernelInjection.class);

    public void run(KernelState kernelState) throws GameException {
        // debug
        PRINTER.printTrace("Test kernel-injected post-processor started...");

        // memory pulse
        PRINTER.printInfo("\n" + MemoryMonitorFactory.getInstance().getMemoryMonitorUtil().getMemoryPulseString());

        // debug
        PRINTER.printTrace("Test kernel-injected post-processor ended...");
    }
}
