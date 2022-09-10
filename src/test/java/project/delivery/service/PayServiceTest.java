package project.delivery.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.*;
import project.delivery.domain.member.Member;
import project.delivery.exception.MaxException;
import project.delivery.exception.NotEnoughBalanceException;
import project.delivery.repository.MemberRepository;
import project.delivery.repository.PayRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static project.delivery.domain.TransactionType.*;

@SpringBootTest
@Transactional
class PayServiceTest {

    @Autowired PayService payService;
    @Autowired PayRepository payRepository;
    @Autowired MemberRepository memberRepository;

    @Test
    @DisplayName("배민페이머니충전")
    void chargePayMoney() {
        Member member = createMember();
        Pay pay = new Pay(member, "012345");
        payRepository.save(pay);

        Long payHistoryId = payService.createPayHistory(member.getId(), 1000000, "test content", CHARGE);

        PayHistory payHistory = pay.getPayHistories().get(0);
        assertThat(payHistory.getId()).isEqualTo(payHistoryId);
        assertThat(pay.getMoney()).isEqualTo(1000000);
    }

    @Test
    @DisplayName("배민페이머니충전-한도초과")
    void chargePayMoney_maximum() {
        Member member = createMember();
        Pay pay = new Pay(member, "012345");
        payRepository.save(pay);

        Long payHistoryId = payService.createPayHistory(member.getId(), 2000000, "test content", CHARGE);

        assertThrows(MaxException.class, () -> {
            payService.createPayHistory(member.getId(), 10000, "test content", CHARGE);
        });
        assertThat(pay.getMoney()).isEqualTo(2000000);
    }

    @Test
    @DisplayName("배민페이머니사용")
    void consumePayMoney() {
        Member member = createMember();
        Pay pay = new Pay(member, "012345");
        payRepository.save(pay);
        payService.createPayHistory(member.getId(), 1000000, "test content", CHARGE);

        Long payHistoryId = payService.createPayHistory(member.getId(), 100000, "test content", USE);

        PayHistory payHistory = pay.getPayHistories().get(1);
        assertThat(payHistory.getId()).isEqualTo(payHistoryId);
        assertThat(pay.getMoney()).isEqualTo(900000);
    }

    @Test
    @DisplayName("배민페이머니사용-잔액부족")
    void consumePayMoney_NotEnough() {
        Member member = createMember();
        Pay pay = new Pay(member, "012345");
        payRepository.save(pay);
        payService.createPayHistory(member.getId(), 100000, "test content", CHARGE);

        assertThrows(NotEnoughBalanceException.class, () -> {
            payService.createPayHistory(member.getId(), 200000, "test content", USE);
        });
        assertThat(pay.getMoney()).isEqualTo(100000);
    }

    @Test
    @DisplayName("배민페이머니환불")
    void refundPayMoney() {
        Member member = createMember();
        Pay pay = new Pay(member, "012345");
        payRepository.save(pay);
        payService.createPayHistory(member.getId(), 1000000, "test content", CHARGE);

        Long payHistoryId = payService.createPayHistory(member.getId(), 100000, "test content", REFUND);

        PayHistory payHistory = pay.getPayHistories().get(1);
        assertThat(payHistory.getId()).isEqualTo(payHistoryId);
        assertThat(pay.getMoney()).isEqualTo(900000);
    }

    @Test
    @DisplayName("배민페이머니환불-잔액부족")
    void refundPayMoney_NotEnough() {
        Member member = createMember();
        Pay pay = new Pay(member, "012345");
        payRepository.save(pay);
        payService.createPayHistory(member.getId(), 100000, "test content", CHARGE);

        assertThrows(NotEnoughBalanceException.class, () -> {
            payService.createPayHistory(member.getId(), 200000, "test content", REFUND);
        });
        assertThat(pay.getMoney()).isEqualTo(100000);
    }

    private Member createMember() {
        Address address = new Address("12345", "mainAddress", "detailAddress");
        Member member = new Member("test@test.com", "test1!", "user", "20010101", "01011111111", "tester", address);
        return memberRepository.save(member);
    }
}