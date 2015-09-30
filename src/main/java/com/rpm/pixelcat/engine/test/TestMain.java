package com.rpm.pixelcat.engine.test;

import com.google.common.collect.ImmutableMap;
import com.rpm.pixelcat.engine.common.Printer;
import com.rpm.pixelcat.engine.common.PrinterFactory;
import com.rpm.pixelcat.engine.hid.HIDEventEnum;
import com.rpm.pixelcat.engine.kernel.*;
import com.rpm.pixelcat.engine.test.enumeration.LevelHandle;

import java.util.HashMap;
import java.util.Map;

public class TestMain {
    private static final Printer PRINTER = PrinterFactory.getInstance().createPrinter(TestMain.class);

    public static void main(String arg[]) {
        try {
            // create kernel
            Kernel kernel = KernelFactory.getInstance().createKernel();

            // set up general kernel state initialization properties
            HashMap<KernelStatePropertyEnum, Object> kernelStateInitProperties = new HashMap<>();
            kernelStateInitProperties.put(KernelStatePropertyEnum.LOG_LVL, Printer.getLogLevelInfo());

            // initialize
            kernel.init(kernelStateInitProperties);
            KernelState kernelState = kernel.getKernelState();

            // set up general kernel action mappings
            KernelActionBinder kernelActionBinder = (KernelActionBinder) kernelState.getProperty(KernelStatePropertyEnum.KERNEL_ACTION_BINDER);
            kernelActionBinder.bind(HIDEventEnum.Q, KernelActionEnum.EXIT);
            kernelActionBinder.unbind(HIDEventEnum.F);
            kernelActionBinder.bind(HIDEventEnum.D, KernelActionEnum.FONT_DEBUG_TOGGLE);

            // init game objects
            LevelHandle startingLevel = LevelHandle.START_SCREEN;
            LevelHandler levelHandler = new LevelHandler(startingLevel);
            GameObjectsHandler gameObjectsHandler = new GameObjectsHandler(kernelState);
            gameObjectsHandler.init();
            kernel.registerGameObjectManagers(gameObjectsHandler.getGameObjectManagerList(startingLevel));

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
