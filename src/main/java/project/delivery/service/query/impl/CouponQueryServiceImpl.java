package project.delivery.service.query.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.coupon.Coupon;
import project.delivery.dto.CouponDto;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.CouponRepository;
import project.delivery.service.query.CouponQueryService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponQueryServiceImpl implements CouponQueryService {

    private final CouponRepository couponRepository;

    @Override
    public List<CouponDto> findCoupons(Long memberId) {
        LocalDateTime period = LocalDateTime.now().minusMonths(6);
        List<Coupon> coupons = couponRepository.findCouponByMemberId(memberId, period);
        return coupons.stream()
                .map(CouponDto::new)
                .toList();
    }

    @Override
    public List<Coupon> findAvailableCoupons(Long memberId) {
        return couponRepository.findAvailableCouponsByMemberId(memberId);
    }

    @Override
    public Integer countAvailableCoupons(Long memberId) {
        return couponRepository.countAvailableCouponsByMemberId(memberId);
    }

    @Override
    public Integer countDayByMemberId(Long memberId) {
        LocalDateTime date = LocalDateTime.now().plusDays(1);
        return couponRepository.countDayByMemberId(memberId, date);
    }

    @Override
    public Coupon findById(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElse(null);
        if (coupon == null) {
            throw new NoSuchException("등록되지 않은 쿠폰입니다");
        }
        return coupon;
    }
}
