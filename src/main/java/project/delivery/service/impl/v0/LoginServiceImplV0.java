package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.Member;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.MemberRepository;
import project.delivery.service.LoginService;

@Service
@RequiredArgsConstructor
public class LoginServiceImplV0 implements LoginService {

    private final MemberRepository memberRepository;

    @Override
    public Member login(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> {
                    throw new NoSuchException("아이디 혹은 비밀번호가 틀렸습니다.");
                });
    }

    @Override
    public String findLoginEmail(String username, String phone) {
        Member findMember = memberRepository.findByUsernameAndPhone(username, phone)
                .orElseThrow(() -> {
                    throw new NoSuchException("등록되지 않은 회원입니다.");
                });
        return findMember.getEmail();
    }

    @Override
    public String findLoginPassword(String username, String email) {
        Member findMember = memberRepository.findByUsernameAndEmail(username, email)
                .orElseThrow(() -> {
                    throw new NoSuchException("등록되지 않은 회원입니다.");
                });
        return findMember.getPassword();

    }
}
