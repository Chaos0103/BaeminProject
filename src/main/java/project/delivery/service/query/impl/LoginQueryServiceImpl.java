package project.delivery.service.query.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.member.Member;
import project.delivery.dto.FindEmailDto;
import project.delivery.repository.MemberRepository;
import project.delivery.service.query.LoginQueryService;

@Service
@RequiredArgsConstructor
public class LoginQueryServiceImpl implements LoginQueryService {

    private final MemberRepository memberRepository;

    @Override
    public Member login(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password)
                .orElse(null);
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
