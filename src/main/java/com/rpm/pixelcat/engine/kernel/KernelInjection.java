package com.rpm.pixelcat.engine.kernel;

import com.rpm.pixelcat.engine.exception.GameException;

public interface KernelInjection {
    public void run(KernelState kernelState) throws GameException;
}
