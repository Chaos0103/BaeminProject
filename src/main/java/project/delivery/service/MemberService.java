package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.dto.MemberDto;

@Transactional(readOnly = true)
public interface MemberService {

    /**
     * 회원가입: 3가지 컬럼(이메일, 연락처, 닉네임)에 대해서 중복검사 시행
     * @return 회원고유번호
     */
    @Transactional
    Long joinMember(MemberDto memberDto);

    //회원수정
    //회원조회
    //회원삭제

}
