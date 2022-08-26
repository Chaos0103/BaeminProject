package project.delivery.admin.coupon;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponData {

    @Id @GeneratedValue
    @Column(name = "coupon_data_id")
    private Long id;

    @Column(updatable = false, nullable = false, length = 16)
    private String couponCode;
    @Column(updatable = false, nullable = false)
    private String couponName;
    @Column(updatable = false, nullable = false)
    private int minOrderPrice;
    @Column(updatable = false, nullable = false)
    private int discountPrice;
    @Column(updatable = false, nullable = false)
    private LocalDateTime expirationDate;
    @Enumerated(EnumType.STRING)
    private CouponDateStatus status;

    public CouponData(String couponCode, String couponName, int minOrderPrice, int discountPrice, LocalDateTime expirationDate) {
        this.couponCode = couponCode;
        this.couponName = couponName;
        this.minOrderPrice = minOrderPrice;
        this.discountPrice = discountPrice;
        this.expirationDate = expirationDate;
        this.status = CouponDateStatus.POSSIBLE;
    }

    //==비즈니스 메서드==//
    public void changeStatus() {
        this.status = CouponDateStatus.IMPOSSIBLE;
    }
}
