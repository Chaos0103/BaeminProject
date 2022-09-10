package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.Address;
import project.delivery.domain.member.Member;

@Transactional(readOnly = true)
public interface MemberService {

    /**
     * 회원가입 로직
     * @param member 저장할 객체
     * @return 회원 PK
     * @exception project.delivery.exception.DuplicateException 이메일, 연락처, 닉네임 중복 예외 발생
     */
    @Transactional
    Long joinMember(Member member);

    /**
     * 회원 닉네임 변경 로직
     * @param memberId 회원 PK
     * @param newNickname 변경할 닉네임
     * @exception project.delivery.exception.NoSuchException 회원이 존재하지 않는 경우
     */
    @Transactional
    void changeNickname(Long memberId, String newNickname);

    /**
     * 회원 비밀번호 변경 로직
     * @param memberId 회원 PK
     * @param newPassword 변경할 비밀번호
     * @exception project.delivery.exception.NoSuchException 회원이 존재하지 않는 경우
     */
    @Transactional
    void changePassword(Long memberId, String newPassword);

    /**
     * 회원 연락처 변경 로직
     * @param memberId 회원 PK
     * @param newPhone 변경할 연락처
     * @exception project.delivery.exception.NoSuchException 회원이 존재하지 않는 경우
     */
    @Transactional
    void changePhone(Long memberId, String newPhone);

    /**
     * 회원 주소 변경 로직
     * @param memberId 회원 PK
     * @param newAddress 변경할 주소
     * @exception project.delivery.exception.NoSuchException 회원이 존재하지 않는 경우
     */
    @Transactional
    void changeAddress(Long memberId, Address newAddress);

    /**
     * 회원탈퇴 로직
     * @param memberId 회원 PK
     * @exception project.delivery.exception.NoSuchException 회원이 존재하지 않는 경우
     */
    @Transactional
    void deleteMember(Long memberId);
}
