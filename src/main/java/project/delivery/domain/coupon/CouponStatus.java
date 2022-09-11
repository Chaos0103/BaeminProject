package project.delivery.domain.coupon;

public enum CouponStatus {
    USE("사용완료"), UNUSE("미사용"), EXPIRE("기간만료");

    private final String description;

    CouponStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
