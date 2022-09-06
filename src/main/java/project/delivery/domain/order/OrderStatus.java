package project.delivery.domain.order;

public enum OrderStatus {
    ORDER("주문접수"), CANCEL("주문취소"), APPROVAL("주문승인"), COMP("조리완료");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
