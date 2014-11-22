package com.rpm.pixelcat.logic.resource.model;

import java.awt.*;

abstract class ResourceImpl extends Rectangle implements Resource {
    ResourceImpl(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
}
