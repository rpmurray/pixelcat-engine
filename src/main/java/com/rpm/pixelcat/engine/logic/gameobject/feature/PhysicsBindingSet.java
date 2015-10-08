package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.physics.PhysicsBinding;

public interface PhysicsBindingSet extends BindingSetTemplate<PhysicsBinding> {
    static PhysicsBindingSet create() throws GameException {
        return Feature.create(PhysicsBindingSetImpl.class);
    }
}
