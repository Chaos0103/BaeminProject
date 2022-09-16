package project.delivery.service.query;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.coupon.Coupon;
import project.delivery.dto.CouponDto;

import java.util.List;

@Transactional(readOnly = true)
public interface CouponQueryService {

    /**
     * 최근 6개월 쿠폰정보를 조회하는 로직
     * @param memberId 회원 PK
     * @return 쿠폰 DTO 리스트
     */
    List<CouponDto> findCoupons(Long memberId);

    /**
     * 이용 가능한 쿠폰정보를 조회하는 로직
     * @param memberId 회원 PK
     * @return
     */
    List<Coupon> findAvailableCoupons(Long memberId);

    /**
     * 이용 가능한 쿠폰갯수를 조회하는 로직
     * @param memberId 회원 PK
     * @return 이용 가능한 쿠폰갯수
     */
    Integer countAvailableCoupons(Long memberId);

    /**
     * 만료일이 하루 남은 쿠폰갯수를 조회하는 로직
     * @param memberId 회원 PK
     * @return 만료일이 하루 남은 쿠폰갯수
     */
    Integer countDayByMemberId(Long memberId);

    /**
     * 결제시 쿠폰 데이터 조회하는 로직
     * @param couponId 쿠폰 PK
     * @return 쿠폰
     */
    Coupon findById(Long couponId);
}
