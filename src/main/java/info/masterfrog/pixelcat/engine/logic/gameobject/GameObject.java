package info.masterfrog.pixelcat.engine.logic.gameobject;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.common.IdGenerator;
import info.masterfrog.pixelcat.engine.logic.gameobject.feature.Feature;

public interface GameObject extends IdGenerator {
    <F extends Feature> GameObject registerFeature(F feature) throws TransientGameException;

    <F extends Feature> GameObject registerFeature(F feature, Boolean status) throws TransientGameException;

    <F extends Feature> Boolean hasFeature(Class<F> featureClass);

    <F extends Feature> void verifyHasFeature(Class<F> featureClass) throws TransientGameException;

    <F extends Feature> F getFeature(Class<F> featureClass) throws TransientGameException;

    <F extends Feature> void deactivateFeature(Class<F> featureClass) throws TransientGameException;

    <F extends Feature> void activateFeature(Class<F> featureClass) throws TransientGameException;

    <F extends Feature> Boolean isFeatureActive(Class<F> featureClass);

    <F extends Feature> void verifyFeatureIsActive(Class<F> featureClass) throws TransientGameException;

    <F extends Feature> Boolean isFeatureAvailable(Class<F> featureClass);

    <F extends Feature> void verifyFeatureIsAvailable(Class<F> featureClass) throws TransientGameException;

    GameObjectProperties getProperties();

    static GameObject create(GameObjectProperties properties) throws TransientGameException {
        GameObject gameObject = new GameObjectImpl(properties);

        return gameObject;
    }
}
