package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.Member;
import project.delivery.repository.MemberRepository;
import project.delivery.service.LoginService;

@Service
@RequiredArgsConstructor
public class LoginServiceImplV0 implements LoginService {

    private final MemberRepository memberRepository;

    @Override
    public Member login(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password)
                .orElse(null);
    }

    @Override
    public Member findLoginEmail(String phone) {
        return memberRepository.findByPhone(phone)
                .orElse(null);
    }

    @Override
    public Long findLoginPassword(String email, String phone) {
        Member findMember = memberRepository.findByEmailAndPhone(email, phone)
                .orElse(null);
        if (findMember == null) {
            return null;
        }
        return findMember.getId();
    }
}
