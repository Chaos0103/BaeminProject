package project.delivery.domain.order;

public enum DeliveryStatus {
    DELIVERY("배달중"), COMP("배달완료");

    private final String description;

    DeliveryStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
