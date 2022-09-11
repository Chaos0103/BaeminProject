package project.delivery.domain.point;

public enum PointType {
    SAVE("적립"), USE("사용"), CHANGE("전환"), EXPIRE("만료");

    private final String description;

    PointType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
