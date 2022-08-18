package project.delivery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.delivery.domain.Address;
import project.delivery.domain.Member;
import project.delivery.repository.MemberRepository;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;

    @PostConstruct
    private void init() {
        Address address = new Address("06544", "서울시 서초구 신반포로 270", "101-101");
        Member member = new Member("lyt1228@naver.com", "pw1!", "임우택", "19980103", "01088888888", "chaos", address);
        memberRepository.save(member);
    }
}
