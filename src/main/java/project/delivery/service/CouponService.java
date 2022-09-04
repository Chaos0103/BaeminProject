package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.Coupon;
import project.delivery.domain.Member;
import project.delivery.dto.CouponDto;

import java.util.List;

@Transactional(readOnly = true)
public interface CouponService {

    @Transactional
    Long addCoupon(Member member, String couponCode);

    List<CouponDto> findCouponByMemberId(Long memberId);

    List<Coupon> findCouponUse(Long memberId);

    Integer countCouponByMemberId(Long memberId);

    Long countDayByMemberId(Long memberId);

    Coupon findById(Long couponId);
}
