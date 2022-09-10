package project.delivery.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.Address;
import project.delivery.domain.member.Member;
import project.delivery.dto.FindEmailDto;
import project.delivery.repository.MemberRepository;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class LoginServiceTest {

    @Autowired LoginService loginService;
    @Autowired MemberRepository memberRepository;

    @Test
    @DisplayName("회원로그인")
    void memberLogin() {
        //given
        Member member = createMember();
        String email = member.getEmail();
        String password = member.getPassword();

        //when
        Member loginMember = loginService.login(email, password);

        //then
        assertThat(loginMember).isEqualTo(member);
    }

    @Test
    @DisplayName("비회원로그인")
    void nonMemberLogin() {
        //given
        String email = "test@test.com";
        String password = "pw1234!@";

        //when
        Member loginMember = loginService.login(email, password);

        //then
        assertThat(loginMember).isNull();
    }

    @Test
    @DisplayName("회원 이메일(아이디)찾기")
    void memberFindEmail() {
        //given
        Member member = createMember();
        String phone = member.getPhone();

        //when
        FindEmailDto findEmailDto = loginService.findEmailByPhone(phone);

        //then
        assertThat(findEmailDto.getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("비회원 이메일(아이디)찾기")
    void nonMemberFindEmail() {
        //given
        String phone = "010-1234-5678";

        //when
        FindEmailDto findEmailDto = loginService.findEmailByPhone(phone);

        //then
        assertThat(findEmailDto).isNull();
    }

    @Test
    @DisplayName("회원 비밀번호 찾기")
    void memberFindPassword() {
        //given
        Member member = createMember();
        String email = member.getEmail();
        String phone = member.getPhone();

        //when
        Long findMemberId = loginService.findMemberIdByEmailAndPhone(email, phone);

        //then
        assertThat(findMemberId).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("비회원 비밀번호 찾기")
    void nonMemberFindPassword() {
        //given
        String email = "test@test.com";
        String phone = "010-1234-5678";

        //when
        Long findMemberId = loginService.findMemberIdByEmailAndPhone(email, phone);

        //then
        assertThat(findMemberId).isNull();
    }

    private Member createMember() {
        Member member = new Member("test@test.com", "pw1234!@", "username", "20010101", "010-1234-5678", "nickname", new Address("12345", "mainAddress", "subAddress"));
        return memberRepository.save(member);
    }
}
