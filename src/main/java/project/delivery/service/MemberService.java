package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.Address;
import project.delivery.domain.Member;

@Transactional(readOnly = true)
public interface MemberService {

    /**
     * 회원가입: 3가지 컬럼(이메일, 연락처, 닉네임)에 대해서 중복검사 시행
     * @return 회원고유번호
     */
    @Transactional
    Long joinMember(Member member);

    /**
     * 회원정보수정: 닉네임 변경
     */
    @Transactional
    void changeNickname(Long memberId, String nickname);

    /**
     * 회원정보수정: 비밀번호 변경
     */
    @Transactional
    void changePassword(Long memberId, String password);

    /**
     * 회원정보수정: 연락처 변경
     */
    @Transactional
    void changePhone(Long memberId, String phone);

    /**
     * 회원정보수정: 주소 변경
     */
    @Transactional
    void changeAddress(Long memberId, Address address);

    /**
     * 회원탈퇴
     */
    @Transactional
    void deleteMember(Long memberId);

    //회원조회
}
