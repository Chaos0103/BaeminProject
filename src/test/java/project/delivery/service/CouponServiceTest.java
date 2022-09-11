package project.delivery.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.delivery.admin.coupon.CouponData;
import project.delivery.admin.coupon.CouponDataRepository;
import project.delivery.admin.coupon.CouponDateStatus;
import project.delivery.domain.Address;
import project.delivery.domain.member.Coupon;
import project.delivery.domain.member.Member;
import project.delivery.dto.CouponDto;
import project.delivery.exception.DuplicateException;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.CouponRepository;
import project.delivery.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CouponServiceTest {

    @Autowired CouponService couponService;
    @Autowired CouponRepository couponRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired CouponDataRepository couponDataRepository;

    private final static String COUPON_NUMBER = "1234123412341234";

    @Test
    @DisplayName("쿠폰등록")
    void couponRegistration() {
        //given
        Member member = createMember();
        couponData();

        //when
        Long couponId = couponService.couponRegistration(member.getId(), COUPON_NUMBER);

        //then
        Coupon findCoupon = couponRepository.findById(couponId).get();
        CouponData findCouponData = couponDataRepository.findByCouponCode(COUPON_NUMBER).get();
        assertThat(findCoupon.getCouponCode()).isEqualTo(COUPON_NUMBER);
        assertThat(findCouponData.getStatus()).isEqualTo(CouponDateStatus.IMPOSSIBLE);
    }

    @Test
    @DisplayName("존재하지 않는 쿠폰번호 입력")
    void couponCodeException() {
        //given
        Member member = createMember();

        //when
        NoSuchException exception = assertThrows(NoSuchException.class,
                () -> couponService.couponRegistration(member.getId(), COUPON_NUMBER));

        //then
        assertThat(exception.getMessage()).isEqualTo("유효하지 않은 쿠폰입니다. 쿠폰코드를 다시한번 확인해주세요.");
    }

    @Test
    @DisplayName("사용된 쿠폰번호 입력")
    void usedCouponCodeException() {
        //given
        Member member = createMember();
        couponData();
        couponService.couponRegistration(member.getId(), COUPON_NUMBER);

        //when
        DuplicateException exception = assertThrows(DuplicateException.class,
                () -> couponService.couponRegistration(member.getId(), COUPON_NUMBER));

        //then
        assertThat(exception.getMessage()).isEqualTo("이미 등록된 쿠폰입니다");
    }

    @Test
    @DisplayName("쿠폰정보 조회")
    void findCouponByMemberId() {
        //given
        Member member = createMember();
        Coupon coupon1 = new Coupon(member, COUPON_NUMBER, "test coupon1", 10000, 1000, LocalDateTime.now().minusMonths(1));
        Coupon coupon2 = new Coupon(member, "0000000000000000", "test coupon2", 20000, 2000, LocalDateTime.now().minusYears(1));
        couponRepository.save(coupon1);
        couponRepository.save(coupon2);

        //when
        List<CouponDto> coupons = couponService.findCouponByMemberId(member.getId());

        //then
        assertThat(coupons.size()).isEqualTo(1);
    }

    private Member createMember() {
        Member member = new Member("test@test.com", "pw1234!@", "username", "20010101", "010-1234-5678", "nickname", new Address("12345", "mainAddress", "subAddress"));
        return memberRepository.save(member);
    }

    private void couponData() {
        CouponData coupon = new CouponData(COUPON_NUMBER, "test coupon", 10000, 1000, LocalDateTime.now().plusDays(3));
        couponDataRepository.save(coupon);
    }
}
