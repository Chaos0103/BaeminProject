package project.delivery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.delivery.admin.CouponData;
import project.delivery.admin.CouponDataRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AdminDateInit {

    private final CouponDataRepository couponDataRepository;

    @PostConstruct
    private void init() {
        CouponData couponData = new CouponData("1234123412341234", "테스트쿠폰", 10000, 1000, LocalDateTime.now().plusMonths(1));
        couponDataRepository.save(couponData);
    }
}
