package project.delivery.service.query;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.member.Member;
import project.delivery.dto.FindEmailDto;
import project.delivery.dto.LoginMember;

@Transactional(readOnly = true)
public interface LoginQueryService {

    /**
     * 로그인
     *
     * @param email
     * @param password
     * @return 회원 엔티티
     */
    LoginMember login(String email, String password);

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
