package com.rpm.pixelcat.logic.gameobject;

import com.rpm.pixelcat.constants.ResourceKeys;
import com.rpm.pixelcat.exception.GameException;
import com.rpm.pixelcat.kernel.KernelState;
import com.rpm.pixelcat.logic.resource.model.ResourceImpl;

import java.io.IOException;
import java.net.URISyntaxException;

public class GameObjectManagerImpl implements GameObjectManager {
    private ResourceImpl[] objects;

    public GameObjectManagerImpl(KernelState kernelState) throws IOException, URISyntaxException {
        objects = new ResourceImpl[ResourceKeys.NUM_OBJS.getValue()];
        objects[ResourceKeys.CHARACTER.getValue()] = new com.rpm.pixelcat.logic.gameobject.Character(kernelState.getBounds());
        objects[ResourceKeys.TITLE.getValue()] = new Title(kernelState.getBounds());
        objects[ResourceKeys.SUBTITLE.getValue()] = new Subtitle(kernelState.getBounds());
    }

    public int getCount() {
        return objects.length;
    }

    public ResourceImpl[] getObjects() {
        return objects;
    }

    public void process(KernelState kernelState) throws GameException {
        for (ResourceImpl object : objects) {
            object.process(kernelState);
        }
    }
}
