package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.Member;
import project.delivery.dto.MemberDto;
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
                    throw new NoSuchException("계정 정보가 일치하지 않습니다.");
                });
    }

    @Override
    public MemberDto findLoginEmail(String phone) {
        Member findMember = memberRepository.findByPhone(phone)
                .orElseThrow(() -> {
                    throw new NoSuchException("등록되지 않은 회원입니다");
                });
        return new MemberDto(findMember);
    }

    @Override
    public Long findLoginPassword(String email, String phone) {
        Member findMember = memberRepository.findByEmailAndPhone(email, phone)
                .orElseThrow(() -> {
                    throw new NoSuchException("등록되지 않은 회원입니다");
                });
        return findMember.getId();
    }
}
