package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.Coupon;
import project.delivery.domain.Member;

import java.util.List;

@Transactional(readOnly = true)
public interface CouponService {

    @Transactional
    Long addCoupon(Member member, String couponCode);

    List<Coupon> findCouponAll(Long memberId);

    List<Coupon> findCouponUse(Long memberId);

    Long countByMemberId(Long memberId);

    Long countDayByMemberId(Long memberId);

    Coupon findById(Long couponId);

    Integer countCoupon(Long memberId);
}
