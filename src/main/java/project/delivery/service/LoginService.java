package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.Member;

@Transactional(readOnly = true)
public interface LoginService {

    /**
     * 로그인
     * @return 로그인 회원
     */
    Member login(String email, String password);

    /**
     * 아이디찾기
     * @return 이메일
     */
    String findLoginEmail(String username, String phone);

    /**
     * 비밀번호찾기
     * @return 비밀번호
     */
    String findLoginPassword(String username, String email);
}
