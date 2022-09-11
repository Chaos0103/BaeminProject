package project.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import project.delivery.domain.member.Coupon;
import project.delivery.domain.member.CouponStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CouponDto {

    private String couponName;
    private Integer discountPrice;
    private LocalDateTime expirationDate;
    private Integer minOrderPrice;
    private CouponStatus status;

    public CouponDto(Coupon coupon) {
        this.couponName = coupon.getCouponName();
        this.discountPrice = coupon.getDiscountPrice();
        this.expirationDate = coupon.getExpirationDate();
        this.minOrderPrice = coupon.getMinOrderPrice();
        this.status = coupon.getStatus();
    }
}
