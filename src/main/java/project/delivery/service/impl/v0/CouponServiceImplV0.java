package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.admin.CouponData;
import project.delivery.admin.CouponDataRepository;
import project.delivery.admin.CouponDateStatus;
import project.delivery.domain.Coupon;
import project.delivery.domain.CouponStatus;
import project.delivery.domain.Member;
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
            throw new NoSuchException("쿠폰번호가 잘못되었습니다");
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
    public List<Coupon> findCoupon(Long memberId) {
        LocalDateTime searchDate = LocalDateTime.now().minusMonths(6);
        return couponRepository.findAllByMemberId(memberId, searchDate);
    }

    @Override
    public Long countByMemberId(Long memberId) {
        return couponRepository.countByMemberId(memberId);
    }

    @Override
    public Long countDayByMemberId(Long memberId) {
        LocalDateTime date = LocalDateTime.now().plusDays(1);
        return couponRepository.countDayByMemberId(memberId, date);
    }

    private static Coupon createCoupon(Member member, CouponData couponData) {
        return new Coupon(member, couponData.getCouponCode(), couponData.getCouponName(), couponData.getMinOrderPrice(), couponData.getDiscountPrice(), couponData.getExpirationDate());
    }
}
