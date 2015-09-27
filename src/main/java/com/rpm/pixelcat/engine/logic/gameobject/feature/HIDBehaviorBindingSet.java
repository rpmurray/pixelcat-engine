package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.gameobject.behavior.HIDBehaviorBinding;

public interface HIDBehaviorBindingSet extends BindingSetTemplate<HIDBehaviorBinding> {
    static HIDBehaviorBindingSet create() throws GameException {
        return Feature.create(HIDBehaviorBindingSet.class);
    }
}
