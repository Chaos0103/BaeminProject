package project.delivery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.delivery.admin.coupon.CouponData;
import project.delivery.admin.coupon.CouponDataRepository;
import project.delivery.admin.voucher.VoucherData;
import project.delivery.admin.voucher.VoucherDataRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AdminDateInit {

    private final CouponDataRepository couponDataRepository;
    private final VoucherDataRepository voucherDataRepository;

    @PostConstruct
    private void init() {
        CouponData couponData = new CouponData("1234123412341234", "테스트쿠폰", 10000, 1000, LocalDateTime.now().plusMonths(1));
        couponDataRepository.save(couponData);

        VoucherData voucherData = new VoucherData("abcd1234abcd", "테스트상품권", 10000, LocalDateTime.now().plusMonths(1));
        voucherDataRepository.save(voucherData);
    }
}
