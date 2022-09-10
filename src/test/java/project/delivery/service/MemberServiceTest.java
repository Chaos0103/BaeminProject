package project.delivery.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.Address;
import project.delivery.domain.member.Member;
import project.delivery.exception.DuplicateException;
import project.delivery.repository.MemberRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입")
    void joinMember() {
        //given
        Member member = new Member("test@test.com", "pw1234!@", "username", "20010101", "010-1234-5678", "nickname", new Address("12345", "mainAddress", "subAddress"));

        //when
        Long memberId = memberService.joinMember(member);

        //then
        Member findMember = memberRepository.findById(memberId).get();
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    @DisplayName("이메일 중복검사")
    void duplicatedEmail() {
        //given
        createMember();
        Member member = new Member("test@test.com", "pw1234!@", "username", "20010101", "010-1111-1111", "newNickname", new Address("12345", "mainAddress", "subAddress"));

        //when
        DuplicateException exception = assertThrows(DuplicateException.class, () -> memberService.joinMember(member));

        //then
        assertThat(exception.getMessage()).isEqualTo("이미 사용중인 이메일입니다.");
    }

    @Test
    @DisplayName("연락처 중복검사")
    void duplicatedPhone() {
        //given
        createMember();
        Member member = new Member("test1@test.com", "pw1234!@", "username", "20010101", "010-1234-5678", "newNickname", new Address("12345", "mainAddress", "subAddress"));

        //when
        DuplicateException exception = assertThrows(DuplicateException.class, () -> memberService.joinMember(member));

        //then
        assertThat(exception.getMessage()).isEqualTo("이미 사용중인 연락처입니다.");
    }

    @Test
    @DisplayName("닉네임 중복검사")
    void duplicatedNickname() {
        //given
        createMember();
        Member member = new Member("test1@test.com", "pw1234!@", "username", "20010101", "010-1111-1111", "nickname", new Address("12345", "mainAddress", "subAddress"));

        //when
        DuplicateException exception = assertThrows(DuplicateException.class, () -> memberService.joinMember(member));

        //then
        assertThat(exception.getMessage()).isEqualTo("이미 사용중인 닉네임입니다.");
    }

    @Test
    @DisplayName("닉네임 변경")
    void changeNickname() {
        //given
        Member member = createMember();
        String newNickname = "new Nickname";

        //when
        memberService.changeNickname(member.getId(), newNickname);

        //then
        assertThat(member.getNickname()).isEqualTo(newNickname);
    }

    @Test
    @DisplayName("비밀번호 변경")
    void changePassword() {
        //given
        Member member = createMember();
        String newPassword = "newPassword";

        //when
        memberService.changePassword(member.getId(), newPassword);

        //then
        assertThat(member.getPassword()).isEqualTo(newPassword);
    }

    @Test
    @DisplayName("연락처 변경")
    void changePhone() {
        //given
        Member member = createMember();
        String phone = "010-9999-9999";

        //when
        memberService.changePhone(member.getId(), phone);

        //then
        assertThat(member.getPhone()).isEqualTo(phone);
    }

    @Test
    @DisplayName("주소 변경")
    void changeAddress() {
        //given
        Member member = createMember();
        Address address = new Address("98765", "newMainAddress", "newDetailAddress");

        //when
        memberService.changeAddress(member.getId(), address);

        //then
        assertThat(member.getAddress()).isEqualTo(address);
    }

    @Test
    @DisplayName("회원탈퇴")
    void deleteMember() {
        //given
        Member member = createMember();

        //when
        memberService.deleteMember(member.getId());

        //then
        Optional<Member> findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isEmpty();
    }

    private Member createMember() {
        Member member = new Member("test@test.com", "pw1234!@", "username", "20010101", "010-1234-5678", "nickname", new Address("12345", "mainAddress", "subAddress"));
        return memberRepository.save(member);
    }
}