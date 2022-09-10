package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.member.Member;
import project.delivery.dto.FindEmailDto;

@Transactional(readOnly = true)
public interface LoginService {

    /**
     * 로그인
     * @param email
     * @param password
     * @return 회원 엔티티
     */
    Member login(String email, String password);

    /**
     * 이메일(아이디)찾기
     * @param phone
     * @return 회원 PK, email, createdDate
     */
    FindEmailDto findEmailByPhone(String phone);

    /**
     * 비밀번호찾기
     * @param email
     * @param phone
     * @return 회원 PK
     */
    Long findMemberIdByEmailAndPhone(String email, String phone);
}
