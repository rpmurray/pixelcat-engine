package com.rpm.pixelcat.engine.common.memory;

import java.text.NumberFormat;

class MemoryMonitorUtilImpl implements MemoryMonitorUtil {
    public String getMemoryPulseString() {
        // setup
        Runtime runtime = Runtime.getRuntime();
        NumberFormat format = NumberFormat.getInstance();

        // lookup memory details
        Long maxMemory = runtime.maxMemory();
        Long allocatedMemory = runtime.totalMemory();
        Long freeMemory = runtime.freeMemory();

        // generate memory pulse string
        String memoryPulseString = String.format(
            "    ===Memory Pulse===\n%12s: %10s K\n%12s: %10s K\n%12s: %10s K\n%12s: %10s K",
            "Free", format.format(freeMemory / 1024),
            "Allocated", format.format(allocatedMemory / 1024),
            "Max", format.format(maxMemory / 1024),
            "Total Free", format.format((freeMemory + (maxMemory - allocatedMemory))/ 1024)
        );

        return memoryPulseString;
    }
}
