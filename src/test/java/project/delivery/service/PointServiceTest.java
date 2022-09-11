package project.delivery.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.delivery.admin.voucher.VoucherData;
import project.delivery.admin.voucher.VoucherDataRepository;
import project.delivery.domain.Address;
import project.delivery.domain.Point;
import project.delivery.domain.member.Member;
import project.delivery.exception.DuplicateException;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.MemberRepository;
import project.delivery.repository.PointRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PointServiceTest {

    @Autowired PointService pointService;
    @Autowired PointRepository pointRepository;

    @Autowired VoucherDataRepository voucherDataRepository;
    @Autowired MemberRepository memberRepository;

    private static final String VOUCHER_CODE = "123412341234";

    @Test
    @DisplayName("상품권 등록")
    void voucherRegistration() {
        //given
        Member member = createMember();
        createVoucherData();

        //when
        Integer chargedPoint = pointService.voucherRegistration(member.getId(), VOUCHER_CODE);

        //then
        assertThat(chargedPoint).isEqualTo(10000);
    }

    @Test
    @DisplayName("미등록 상품권 번호 등록")
    void nonVoucherCodeException() {
        //given
        Member member = createMember();

        //when
        NoSuchException exception = assertThrows(NoSuchException.class, () -> pointService.voucherRegistration(member.getId(), VOUCHER_CODE));

        //then
        assertThat(exception.getMessage()).isEqualTo("상품권 번호를 다시 확인해주세요.");
    }

    @Test
    @DisplayName("사용된 상품권 번호 등록")
    void usingVoucherCodeException() {
        //given
        Member member = createMember();
        createVoucherData();
        pointService.voucherRegistration(member.getId(), VOUCHER_CODE);

        //when
        DuplicateException exception = assertThrows(DuplicateException.class, () -> pointService.voucherRegistration(member.getId(), VOUCHER_CODE));

        //then
        assertThat(exception.getMessage()).isEqualTo("이미 등록된 상품권 번호입니다.");
    }

    @Test
    @DisplayName("포인트 내역 조회")
    void pointHistory() {
        //given
        Member member = createMember();
        createVoucherData();
        pointService.voucherRegistration(member.getId(), VOUCHER_CODE);

        //when
        Point findPoint = pointRepository.findByMemberId(member.getId()).get();

        //then
        assertThat(findPoint.getPointHistories().size()).isEqualTo(1);
    }

    private Member createMember() {
        Member member = new Member("test@test.com", "pw1234!@", "username", "20010101", "010-1234-5678", "nickname", new Address("12345", "mainAddress", "subAddress"));
        memberRepository.save(member);
        Point point = new Point(member);
        pointRepository.save(point);
        return member;
    }

    private void createVoucherData() {
        voucherDataRepository.save(new VoucherData(VOUCHER_CODE, "test voucher", 10000, LocalDateTime.now().plusMonths(1)));
    }
}
