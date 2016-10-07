package info.masterfrog.pixelcat.engine.logic.gameobject.behavior;

public enum BehaviorEnum {
    MOVE_UP(BehaviorClassEnum.MOVEMENT),
    MOVE_DOWN(BehaviorClassEnum.MOVEMENT),
    MOVE_LEFT(BehaviorClassEnum.MOVEMENT),
    MOVE_RIGHT(BehaviorClassEnum.MOVEMENT),
    ANIMATION_PLAY(BehaviorClassEnum.ANIMATION),
    ANIMATION_STOP(BehaviorClassEnum.ANIMATION),
    CAMERA_SWITCH(BehaviorClassEnum.CAMERA),
    LOGIC_ROUTINE(BehaviorClassEnum.LOGIC),
    SOUND_PLAY(BehaviorClassEnum.SOUND),
    SOUND_PAUSE(BehaviorClassEnum.SOUND),
    SOUND_STOP(BehaviorClassEnum.SOUND),
    SOUND_VOLUME(BehaviorClassEnum.SOUND),
    ;

    public enum BehaviorClassEnum {
        MOVEMENT,
        ANIMATION,
        CAMERA,
        LOGIC,
        SOUND
    }

    private BehaviorClassEnum behaviorClass;

    BehaviorEnum(BehaviorClassEnum behaviorClass) {
        this.behaviorClass = behaviorClass;
    }

    public BehaviorClassEnum getBehaviorClass() {
        return behaviorClass;
    }

    @Override
    public String toString() {
        return "BehaviorEnum{" +
            "name=" + name() +
            '}';
    }
}
