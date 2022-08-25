package project.delivery.domain;

public enum MemberGrade {

    /**
     * default value: BASIC
     */
    BASIC("고마운분"), BRONZE("귀한분"), SILVER("더귀한분"), GOLD("천생연분");

    private final String description;

    MemberGrade(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
