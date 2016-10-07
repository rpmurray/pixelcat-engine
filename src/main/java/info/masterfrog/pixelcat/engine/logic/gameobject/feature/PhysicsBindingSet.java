package info.masterfrog.pixelcat.engine.logic.gameobject.feature;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.physics.PhysicsBinding;

public interface PhysicsBindingSet extends BindingSetTemplate<PhysicsBinding> {
    static PhysicsBindingSet create() throws TransientGameException {
        return Feature.create(PhysicsBindingSetImpl.class);
    }
}
