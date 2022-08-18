package project.delivery.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.Address;
import project.delivery.domain.Member;
import project.delivery.domain.MemberGrade;
import project.delivery.dto.create.CreateAddressDto;
import project.delivery.dto.create.CreateMemberDto;
import project.delivery.exception.DuplicateException;
import project.delivery.repository.MemberRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    @DisplayName("회원가입")
    void joinMember() {
        CreateAddressDto addressDto = new CreateAddressDto("12345", "서울특별시", "101-101");
        CreateMemberDto memberDto = new CreateMemberDto("baemin@baemin.com", "baemin1!", "baemin", "20010101", "01012345678", "baemin", addressDto);

        Long memberId = memberService.joinMember(memberDto);

        Optional<Member> findMember = memberRepository.findById(memberId);
        assertThat(findMember).isPresent();
    }

    @Test
    @DisplayName("회원가입-등급")
    void joinMember_grade() {
        CreateAddressDto addressDto = new CreateAddressDto("12345", "서울특별시", "101-101");
        CreateMemberDto memberDto = new CreateMemberDto("baemin@baemin.com", "baemin1!", "baemin", "20010101", "01012345678", "baemin", addressDto);

        Long memberId = memberService.joinMember(memberDto);

        Member findMember = memberRepository.findById(memberId).get();
        assertThat(findMember.getGrade()).isEqualTo(MemberGrade.BASIC);
    }

    @Test
    @DisplayName("회원가입-시간")
    void joinMember_time() {
        CreateAddressDto addressDto = new CreateAddressDto("12345", "서울특별시", "101-101");
        CreateMemberDto memberDto = new CreateMemberDto("baemin@baemin.com", "baemin1!", "baemin", "20010101", "01012345678", "baemin", addressDto);

        Long memberId = memberService.joinMember(memberDto);

        Member findMember = memberRepository.findById(memberId).get();
        assertThat(findMember.getCreatedDate()).isNotNull();
        assertThat(findMember.getCreatedDate()).isEqualTo(findMember.getLastModifiedDate());
    }

    @Test
    @DisplayName("회원가입-이메일 중복예외")
    void joinMember_duplicatedEmail() {
        Member savedMember = createMember();

        CreateAddressDto addressDto = new CreateAddressDto("12345", "서울특별시", "101-101");
        CreateMemberDto memberDto = new CreateMemberDto("test@test.com", "baemin1!", "baemin", "20010101", "01012345678", "baemin", addressDto);

        assertThrows(DuplicateException.class, () -> memberService.joinMember(memberDto));
    }

    @Test
    @DisplayName("회원가입-연락처 중복예외")
        void joinMember_duplicatedPhone() {
        Member savedMember = createMember();

        CreateAddressDto addressDto = new CreateAddressDto("12345", "서울특별시", "101-101");
        CreateMemberDto memberDto = new CreateMemberDto("baemin@baemin.com", "baemin1!", "baemin", "20010101", "01011111111", "baemin", addressDto);

        assertThrows(DuplicateException.class, () -> memberService.joinMember(memberDto));
    }

    @Test
    @DisplayName("회원가입-닉네임 중복예외")
    void joinMember_duplicatedNickname() {
        Member savedMember = createMember();

        CreateAddressDto addressDto = new CreateAddressDto("12345", "서울특별시", "101-101");
        CreateMemberDto memberDto = new CreateMemberDto("baemin@baemin.com", "baemin1!", "baemin", "20010101", "01012345678", "tester", addressDto);

        assertThrows(DuplicateException.class, () -> memberService.joinMember(memberDto));
    }

    @Test
    @DisplayName("회원정보수정-닉네임")
    void changeNickname() {
        Member member = createMember();

        memberService.changeNickname(member.getId(), "newNickname");

        Member findMember = memberRepository.findById(member.getId()).get();
        assertThat(findMember.getNickname()).isEqualTo("newNickname");
    }

    @Test
    @DisplayName("회원정보수정-비밀번호")
    void changePassword() {
        Member member = createMember();

        memberService.changePassword(member.getId(), "newPassword");

        Member findMember = memberRepository.findById(member.getId()).get();
        assertThat(findMember.getPassword()).isEqualTo("newPassword");
    }

    @Test
    @DisplayName("회원정보수정-연락처")
        void changePhone() {
        Member member = createMember();

        memberService.changePhone(member.getId(), "01087654321");

        Member findMember = memberRepository.findById(member.getId()).get();
        assertThat(findMember.getPhone()).isEqualTo("01087654321");
    }

    @Test
    @DisplayName("회원정보수정-주소")
    void changeAddress() {
        Member member = createMember();
        CreateAddressDto addressDto = new CreateAddressDto("54321", "newMainAddress", "newDetailAddress");

        memberService.changeAddress(member.getId(), addressDto);

        Member findMember = memberRepository.findById(member.getId()).get();
        assertThat(findMember.getAddress().getMainAddress()).isEqualTo("newMainAddress");
    }

    @Test
    @DisplayName("회원탈퇴")
    void deleteMember() {
        Member member = createMember();

        memberService.deleteMember(member.getId());

        Optional<Member> findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isEmpty();
    }

    private Member createMember() {
        Address address = new Address("12345", "mainAddress", "detailAddress");
        Member member = new Member("test@test.com", "test1!", "user", "20010101", "01011111111", "tester", address);
        return memberRepository.save(member);
    }
}