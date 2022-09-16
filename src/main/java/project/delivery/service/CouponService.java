package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CouponService {

    /**
     * 쿠폰 등록 로직
     * @param memberId 회원 PK
     * @param couponCode 16자리 쿠폰번호
     * @return 쿠폰 PK
     * @exception project.delivery.exception.NoSuchException 회원 PK, 쿠폰번호가 존재하지 않는 경우
     * @exception project.delivery.exception.DuplicateException 이미 사용된 쿠폰번호인 경우
     */
    Long couponRegistration(Long memberId, String couponCode);
}
