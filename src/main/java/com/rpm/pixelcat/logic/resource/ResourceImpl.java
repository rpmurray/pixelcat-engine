package com.rpm.pixelcat.logic.resource;

import java.awt.*;

abstract class ResourceImpl extends Rectangle implements Resource {
    ResourceImpl(Integer width, Integer height) {
        super(width, height);
    }
}
