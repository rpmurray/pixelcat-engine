package com.rpm.pixelcat.engine.test;

import com.google.common.collect.ImmutableMap;
import com.rpm.pixelcat.engine.common.Printer;
import com.rpm.pixelcat.engine.common.PrinterFactory;
import com.rpm.pixelcat.engine.kernel.Kernel;
import com.rpm.pixelcat.engine.kernel.KernelFactory;
import com.rpm.pixelcat.engine.kernel.KernelInjection;
import com.rpm.pixelcat.engine.kernel.KernelInjectionEventEnum;

import java.util.Map;

public class TestMain {
    private static final Printer PRINTER = PrinterFactory.getInstance().createPrinter(TestMain.class);

    public static void main(String arg[]) {
        try {
            // create kernel
            Kernel kernel = KernelFactory.getInstance().createKernel();

            // initialize
            kernel.init();

            // init game objects
            String startingLevel = LevelHandler.START_SCREEN;
            LevelHandler levelHandler = new LevelHandler(startingLevel);
            GameObjectsHandler gameObjectsHandler = new GameObjectsHandler(kernel.getKernelState());
            gameObjectsHandler.init();
            kernel.registerGameObjectManagers(gameObjectsHandler.getGameObjectManagerSet(startingLevel));

            // define kernel injections
            Map<KernelInjectionEventEnum, KernelInjection> kernelInjectionMap = ImmutableMap.<KernelInjectionEventEnum, KernelInjection>of(
                KernelInjectionEventEnum.PRE_PROCESSING, new PreProcessingKernelInjection(levelHandler, gameObjectsHandler)
            );

            // run the game kernel
            kernel.kernelMain(kernelInjectionMap);
        } catch (Exception e) {
            PRINTER.printError(e);
        }

        // exit
        System.exit(0);
    }
}
