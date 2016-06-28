package com.rpm.pixelcat.engine.kernel;

import com.rpm.pixelcat.engine.exception.TransientGameException;

public interface KernelInjection {
    public void run(KernelState kernelState) throws TransientGameException;
}
