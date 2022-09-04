package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.admin.coupon.CouponData;
import project.delivery.admin.coupon.CouponDataRepository;
import project.delivery.admin.coupon.CouponDateStatus;
import project.delivery.domain.Coupon;
import project.delivery.domain.Member;
import project.delivery.dto.CouponDto;
import project.delivery.exception.DuplicateException;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.CouponRepository;
import project.delivery.service.CouponService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImplV0 implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponDataRepository couponDataRepository;

    @Override
    public Long addCoupon(Member member, String couponCode) {
        CouponData couponData = couponDataRepository.findByCouponCode(couponCode).orElse(null);
        if (couponData == null) {
            throw new NoSuchException("유효하지 않은 쿠폰입니다. 쿠폰코드를 다시한번 확인해주세요.");
        }
        if (couponData.getStatus() == CouponDateStatus.IMPOSSIBLE) {
            throw new DuplicateException("이미 등록된 쿠폰입니다");
        }
        couponData.changeStatus();
        Coupon coupon = createCoupon(member, couponData);
        Coupon savedCoupon = couponRepository.save(coupon);
        return savedCoupon.getId();
    }

    @Override
    public List<CouponDto> findCouponByMemberId(Long memberId) {
        LocalDateTime period = LocalDateTime.now().minusMonths(6);
        return couponRepository.findCouponByMemberId(memberId, period);
    }

    @Override
    public List<Coupon> findCouponUse(Long memberId) {
        return couponRepository.findCouponUse(memberId);
    }

    @Override
    public Integer countCouponByMemberId(Long memberId) {
        return couponRepository.countByMemberId(memberId);
    }

    @Override
    public Long countDayByMemberId(Long memberId) {
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

    private static Coupon createCoupon(Member member, CouponData couponData) {
        return new Coupon(member, couponData.getCouponCode(), couponData.getCouponName(), couponData.getMinOrderPrice(), couponData.getDiscountPrice(), couponData.getExpirationDate());
    }
}
