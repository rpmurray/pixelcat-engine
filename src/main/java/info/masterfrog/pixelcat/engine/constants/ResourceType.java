package info.masterfrog.pixelcat.engine.constants;

public enum ResourceType {
    IMAGE("images"),
    FONT("fonts"),
    SOUND("sounds"),
    XML("xml"),
    ;

    private final String resourceFilePath;

    ResourceType(String resourceFilePath) {
        this.resourceFilePath = resourceFilePath;
    }

    public String getResourceFilePath() {
        return resourceFilePath;
    }

    @Override
    public String toString() {
        return "ResourceType{" +
            "resourceFilePath='" + resourceFilePath + '\'' +
            '}';
    }
}
