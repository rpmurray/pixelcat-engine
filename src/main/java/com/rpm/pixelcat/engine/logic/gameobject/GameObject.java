package com.rpm.pixelcat.engine.logic.gameobject;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.gameobject.feature.Feature;

public interface GameObject {
    public void registerFeature(Feature feature, Boolean status);

    public <F extends Feature> Boolean hasFeature(Class<F> featureClass);

    public <F extends Feature> F getFeature(Class<F> featureClass) throws GameException;

    public <F extends Feature> void deactivateFeature(Class<F> featureClass) throws GameException;

    public <F extends Feature> void activateFeature(Class<F> featureClass) throws GameException;

    public <F extends Feature> Boolean isFeatureActive(Class<F> featureClass) throws GameException;
}
