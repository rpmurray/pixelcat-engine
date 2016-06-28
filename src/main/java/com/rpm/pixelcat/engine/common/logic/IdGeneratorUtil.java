package com.rpm.pixelcat.engine.common.logic;

import java.util.UUID;

public class IdGeneratorUtil {
    public static String generateId(String base) {
        // generate uniqifier
        String uniquifier = UUID.randomUUID().toString();
        //String uniquifier = String.valueOf(System.nanoTime());

        // generate full id
        String id = base.isEmpty() ? uniquifier : base + "-" + uniquifier;

        return id;
    }
}
