package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.admin.coupon.CouponData;
import project.delivery.admin.coupon.CouponDataRepository;
import project.delivery.admin.coupon.CouponDateStatus;
import project.delivery.domain.coupon.Coupon;
import project.delivery.domain.member.Member;
import project.delivery.dto.CouponDto;
import project.delivery.exception.DuplicateException;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.CouponRepository;
import project.delivery.repository.MemberRepository;
import project.delivery.service.CouponService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImplV0 implements CouponService {

    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final CouponDataRepository couponDataRepository;

    @Override
    public Long couponRegistration(Long memberId, String couponCode) {
        Member findMember = memberRepository.findById(memberId).orElse(null);
        if (findMember == null) {
            throw new NoSuchException("등록되지 않은 회원입니다");
        }
        CouponData couponData = couponDataRepository.findByCouponCode(couponCode).orElse(null);
        if (couponData == null) {
            throw new NoSuchException("유효하지 않은 쿠폰입니다. 쿠폰코드를 다시한번 확인해주세요.");
        }
        if (couponData.getStatus() == CouponDateStatus.IMPOSSIBLE) {
            throw new DuplicateException("이미 등록된 쿠폰입니다");
        }
        couponData.changeStatus();
        Coupon coupon = createCoupon(findMember, couponData);
        Coupon savedCoupon = couponRepository.save(coupon);
        return savedCoupon.getId();
    }

    @Override
    public List<CouponDto> findCouponByMemberId(Long memberId) {
        LocalDateTime period = LocalDateTime.now().minusMonths(6);
        List<Coupon> coupons = couponRepository.findCouponByMemberId(memberId, period);
        return coupons.stream()
                .map(CouponDto::new)
                .toList();
    }

    @Override
    public List<Coupon> findAvailableCouponsByMemberId(Long memberId) {
        return couponRepository.findAvailableCouponsByMemberId(memberId);
    }

    @Override
    public Integer countAvailableCouponsByMemberId(Long memberId) {
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

    private static Coupon createCoupon(Member member, CouponData couponData) {
        return new Coupon(member, couponData.getCouponCode(), couponData.getCouponName(), couponData.getMinOrderPrice(), couponData.getDiscountPrice(), couponData.getExpirationDate());
    }
}
