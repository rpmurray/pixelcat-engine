package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.TransientGameException;
import com.rpm.pixelcat.engine.logic.physics.PhysicsBinding;

public interface PhysicsBindingSet extends BindingSetTemplate<PhysicsBinding> {
    static PhysicsBindingSet create() throws TransientGameException {
        return Feature.create(PhysicsBindingSetImpl.class);
    }
}
