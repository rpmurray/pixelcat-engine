package com.rpm.pixelcat.engine.logic.gameobject;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.common.IdGenerator;
import com.rpm.pixelcat.engine.logic.gameobject.feature.Feature;

public interface GameObject extends IdGenerator {
    <F extends Feature> GameObject registerFeature(F feature) throws GameException;

    <F extends Feature> GameObject registerFeature(F feature, Boolean status) throws GameException;

    <F extends Feature> Boolean hasFeature(Class<F> featureClass);

    <F extends Feature> F getFeature(Class<F> featureClass) throws GameException;

    <F extends Feature> void deactivateFeature(Class<F> featureClass) throws GameException;

    <F extends Feature> void activateFeature(Class<F> featureClass) throws GameException;

    <F extends Feature> Boolean isFeatureActive(Class<F> featureClass) throws GameException;

    <F extends Feature> Boolean isFeatureAvailable(Class<F> featureClass);

    GameObjectProperties getProperties();

    static GameObject create(GameObjectProperties properties) throws GameException {
        GameObject gameObject = new GameObjectImpl(properties);

        return gameObject;
    }
}
