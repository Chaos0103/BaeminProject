package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.CouponStatus;

import java.time.LocalDateTime;

@Data
public class CouponDto {

    private String couponName;
    private Integer discountPrice;
    private LocalDateTime expirationDate;
    private Integer minOrderPrice;
    private CouponStatus status;
}
