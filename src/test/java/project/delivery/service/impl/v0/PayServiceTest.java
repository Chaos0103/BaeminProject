package project.delivery.service.impl.v0;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.Address;
import project.delivery.domain.member.Member;
import project.delivery.domain.pay.Pay;
import project.delivery.repository.MemberRepository;
import project.delivery.repository.PayRepository;
import project.delivery.service.PayService;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PayServiceTest {

    @Autowired PayService payService;
    @Autowired PayRepository payRepository;
    @Autowired MemberRepository memberRepository;

    @Test
    @DisplayName("배민페이머니 충전금액 확인")
    void chargePayMoney() {
        //given
        Member member = createMember();
        Pay pay = createPay(member);

        //when
        Long payHistoryId = payService.chargePayMoney(member.getId(), 10000, "test content");

        //then
        assertThat(pay.getMoney()).isEqualTo(10000);
    }

    @Test
    @DisplayName("배민페이머니 충전시 내역 확인")
    void chargePayHistory() {
        //given
        Member member = createMember();
        Pay pay = createPay(member);

        //when
        Long payHistoryId = payService.chargePayMoney(member.getId(), 10000, "test content");

        //then
        assertThat(pay.getPayHistories().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("배민페이머니 환불금액 확인")
    void refundPayMoney() {
        //given
        Member member = createMember();
        Pay pay = createPay(member);

        //when
        Long payHistoryId = payService.refundPayMoney(member.getId(), "test content");

        //then
        assertThat(pay.getMoney()).isEqualTo(0);
    }

    @Test
    @DisplayName("배민페이머니 환불시 내역 확인")
    void refundPayHistory() {
        //given
        Member member = createMember();
        Pay pay = createPay(member);

        //when
        Long payHistoryId = payService.refundPayMoney(member.getId(), "test content");

        //then
        assertThat(pay.getPayHistories().size()).isEqualTo(1);
    }

    private Member createMember() {
        Member member = new Member("test@test.com", "pw1234!@", "username", "20010101", "010-1234-5678", "nickname", new Address("12345", "mainAddress", "subAddress"));
        return memberRepository.save(member);
    }

    private Pay createPay(Member member) {
        Pay pay = new Pay(member, "000000");
        return payRepository.save(pay);
    }

}