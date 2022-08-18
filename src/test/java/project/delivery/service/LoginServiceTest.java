package project.delivery.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.Address;
import project.delivery.domain.Member;
import project.delivery.dto.MemberDto;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.MemberRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LoginServiceTest {

    @Autowired LoginService loginService;
    @Autowired MemberRepository memberRepository;

    @Test
    @DisplayName("로그인")
    void login() {
        Member member = createMember();

        Member loginMember = loginService.login(member.getEmail(), member.getPassword());

        assertThat(loginMember).isEqualTo(member);
    }

    @Test
    @DisplayName("로그인-이메일 오류")
    void login_email() {
        Member member = createMember();

        assertThrows(NoSuchException.class, () -> {
            loginService.login("abc@test.com", member.getPassword());
        });
    }

    @Test
    @DisplayName("로그인-비밀번호 오류")
    void login_password() {
        Member member = createMember();

        assertThrows(NoSuchException.class, () -> {
            loginService.login(member.getEmail(), "none");
        });
    }

    @Test
    @DisplayName("이메일 찾기")
    void findLoginEmail() {
        Member member = createMember();

        MemberDto memberDto = loginService.findLoginEmail(member.getPhone());

        assertThat(memberDto.getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    @DisplayName("이메일 찾기-연락처 오류")
    void findLoginEmail_phone() {
        Member member = createMember();

        assertThrows(NoSuchException.class, () -> {
            loginService.findLoginEmail("01012345678");
        });
    }

    @Test
    @DisplayName("비밀번호 찾기")
    void findLoginPassword() {
        Member member = createMember();

        Long memberId = loginService.findLoginPassword(member.getEmail(), member.getPhone());

        assertThat(memberId).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("비밀번호 찾기-이메일 오류")
    void findLoginPassword_email() {
        Member member = createMember();

        assertThrows(NoSuchException.class, () -> {
            loginService.findLoginPassword("none@test.com", member.getPhone());
        });
    }

    @Test
    @DisplayName("비밀번호 찾기-연락처 오류")
    void findLoginPassword_username() {
        Member member = createMember();

        assertThrows(NoSuchException.class, () -> {
            loginService.findLoginPassword(member.getEmail(), "01012345678");
        });
    }

    private Member createMember() {
        Address address = new Address("12345", "mainAddress", "detailAddress");
        Member member = new Member("test@test.com", "test1!", "user", "20010101", "01011111111", "tester", address);
        return memberRepository.save(member);
    }
}