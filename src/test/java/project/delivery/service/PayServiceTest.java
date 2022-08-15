package project.delivery.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.Address;
import project.delivery.domain.Member;
import project.delivery.domain.Pay;
import project.delivery.domain.PayHistory;
import project.delivery.exception.MaxException;
import project.delivery.repository.MemberRepository;
import project.delivery.repository.PayRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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

        Long payHistoryId = payService.chargePayMoney(member.getId(), 1000000);

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

        payService.chargePayMoney(member.getId(), 2000000);

        assertThrows(MaxException.class, () -> {
            payService.chargePayMoney(member.getId(), 10000);
        });
        assertThat(pay.getMoney()).isEqualTo(2000000);
    }

    private Member createMember() {
        Address address = new Address("12345", "mainAddress", "detailAddress");
        Member member = new Member("test@test.com", "test1!", "user", "20010101", "01011111111", "tester", address);
        return memberRepository.save(member);
    }
}