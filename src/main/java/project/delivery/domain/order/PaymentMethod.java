package project.delivery.domain.order;

public enum PaymentMethod {
    BAEMINPAY("배민페이"), CARD("신용/체크카드"), PHONE("휴대폰결제"), NAVERPAY("네이버페이"), TOSS("토스");

    private final String description;

    PaymentMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
