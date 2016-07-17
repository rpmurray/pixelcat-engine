package info.masterfrog.pixelcat.engine.logic.gameobject.behavior;

public class BehaviorParameterId implements BehaviorParameter {
    private String id;

    BehaviorParameterId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "BehaviorParameterId{" +
            "id=" + id +
            '}';
    }
}
