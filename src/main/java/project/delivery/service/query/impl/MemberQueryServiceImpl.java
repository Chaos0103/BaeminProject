package project.delivery.service.query.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.member.Member;
import project.delivery.dto.LoginMember;
import project.delivery.repository.MemberRepository;
import project.delivery.service.query.MemberQueryService;

@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository memberRepository;

    @Override
    public LoginMember findLoginMember(Long memberId) {
        Member findLoginMember = memberRepository.findLoginMember(memberId);
        return new LoginMember(findLoginMember);
    }
}
