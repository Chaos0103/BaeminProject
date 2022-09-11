package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.member.Coupon;
import project.delivery.dto.CouponDto;

import java.util.List;

@Transactional(readOnly = true)
public interface CouponService {

    /**
     * 쿠폰 등록 로직
     * @param memberId 회원 PK
     * @param couponCode 16자리 쿠폰번호
     * @return 쿠폰 PK
     * @exception project.delivery.exception.NoSuchException 회원 PK, 쿠폰번호가 존재하지 않는 경우
     * @exception project.delivery.exception.DuplicateException 이미 사용된 쿠폰번호인 경우
     */
    @Transactional
    Long couponRegistration(Long memberId, String couponCode);

    /**
     * 최근 6개월 쿠폰정보를 조회하는 로직
     * @param memberId 회원 PK
     * @return 쿠폰 DTO 리스트
     */
    List<CouponDto> findCouponByMemberId(Long memberId);

    /**
     * 이용 가능한 쿠폰정보를 조회하는 로직
     * @param memberId 회원 PK
     * @return
     */
    List<Coupon> findAvailableCouponsByMemberId(Long memberId);

    /**
     * 이용 가능한 쿠폰갯수를 조회하는 로직
     * @param memberId 회원 PK
     * @return 이용 가능한 쿠폰갯수
     */
    Integer countAvailableCouponsByMemberId(Long memberId);

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
