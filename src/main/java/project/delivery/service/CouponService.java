package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.Coupon;
import project.delivery.domain.Member;

import java.util.List;

@Transactional(readOnly = true)
public interface CouponService {

    @Transactional
    Long addCoupon(Member member, String couponCode);

    List<Coupon> findCoupon(Long memberId);

    Long countByMemberId(Long memberId);

    Long countDayByMemberId(Long memberId);
}
