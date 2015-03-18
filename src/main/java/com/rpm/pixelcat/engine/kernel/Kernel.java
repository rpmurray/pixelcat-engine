package com.rpm.pixelcat.engine.kernel;

import com.rpm.pixelcat.engine.exception.GameException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public interface Kernel {
    public void init() throws IOException, URISyntaxException, GameException;

    public void kernelMain(Map<KernelInjectionEventEnum, KernelInjection> kernelInjectionMap) throws Exception;
}
