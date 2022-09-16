package project.delivery.service.query.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.member.Member;
import project.delivery.dto.FindEmailDto;
import project.delivery.dto.LoginMember;
import project.delivery.repository.MemberRepository;
import project.delivery.service.query.LoginQueryService;

@Service
@RequiredArgsConstructor
public class LoginQueryServiceImpl implements LoginQueryService {

    private final MemberRepository memberRepository;

    @Override
    public LoginMember login(String email, String password) {
        Member findLoginMember = memberRepository.login(email, password).orElse(null);
        if (findLoginMember == null) {
            return null;
        }
        return new LoginMember(findLoginMember);
    }

    @Override
    public FindEmailDto findEmailByPhone(String phone) {
        Member findMember = memberRepository.findEmailByPhone(phone)
                .orElse(null);
        if (findMember == null) {
            return null;
        }
        return new FindEmailDto(findMember.getId(), findMember.getEmail(), findMember.getCreatedDate());
    }

    @Override
    public Long findMemberIdByEmailAndPhone(String email, String phone) {
        Member findMember = memberRepository.findMemberIdByEmailAndPhone(email, phone)
                .orElse(null);
        if (findMember == null) {
            return null;
        }
        return findMember.getId();
    }
}
