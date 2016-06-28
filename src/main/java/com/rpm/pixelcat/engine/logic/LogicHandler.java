package com.rpm.pixelcat.engine.logic;

import com.rpm.pixelcat.engine.exception.ExitException;
import com.rpm.pixelcat.engine.exception.TerminalGameException;
import com.rpm.pixelcat.engine.exception.TransientGameException;
import com.rpm.pixelcat.engine.kernel.KernelState;
import com.rpm.pixelcat.engine.logic.gameobject.GameObject;
import com.rpm.pixelcat.engine.logic.resource.SoundResource;
import com.rpm.pixelcat.engine.sound.SoundEngine;

import java.util.List;
import java.util.Map;

public interface LogicHandler {
    List<List<GameObject>> getLayeredGameObjects(KernelState kernelState) throws TransientGameException;

    Map<SoundEngine.SoundResourceState, SoundResource> getSoundEvents();

    void process(KernelState kernelState) throws TransientGameException, TerminalGameException, ExitException;
}
