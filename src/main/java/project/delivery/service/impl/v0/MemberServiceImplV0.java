package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.Address;
import project.delivery.domain.member.Member;
import project.delivery.domain.Pay;
import project.delivery.domain.Point;
import project.delivery.exception.DuplicateException;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.MemberRepository;
import project.delivery.repository.PayRepository;
import project.delivery.repository.PointRepository;
import project.delivery.service.MemberService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImplV0 implements MemberService {

    private final MemberRepository memberRepository;
    private final PointRepository pointRepository;
    private final PayRepository payRepository;

    @Override
    public Long joinMember(Member member) {
        duplicatedEmail(member.getEmail());
        duplicatedPhone(member.getPhone());
        duplicatedNickname(member.getNickname());

        Member savedMember = memberRepository.save(member);

        Point point = new Point(savedMember);
        pointRepository.save(point);

        Pay pay = new Pay(savedMember, "000000");
        payRepository.save(pay);

        return savedMember.getId();
    }

    @Override
    public void changeNickname(Long memberId, String nickname) {
        Member findMember = getMember(memberId);

        findMember.changeNickname(nickname);
    }

    @Override
    public void changePassword(Long memberId, String password) {
        Member findMember = getMember(memberId);

        findMember.changePassword(password);
    }

    @Override
    public void changePhone(Long memberId, String phone) {
        Member findMember = getMember(memberId);

        findMember.changePhone(phone);
    }

    @Override
    public void changeAddress(Long memberId, Address address) {
        Member findMember = getMember(memberId);

        findMember.changeAddress(address);
    }

    @Override
    public void deleteMember(Long memberId) {
        Member findMember = getMember(memberId);

        memberRepository.delete(findMember);
    }

    /**
     * 이메일 중복검사
     */
    private void duplicatedEmail(String email) {
        Optional<Member> findMember = memberRepository.findByEmail(email);
        if (findMember.isPresent()) {
            throw new DuplicateException("이미 사용중인 이메일입니다.");
        }
    }

    /**
     * 연락처 중복검사
     */
    private void duplicatedPhone(String phone) {
        Optional<Member> findMember = memberRepository.findEmailByPhone(phone);
        if (findMember.isPresent()) {
            throw new DuplicateException("이미 사용중인 연락처입니다.");
        }
    }

    /**
     * 닉네임 중복검사
     */
    private void duplicatedNickname(String nickname) {
        Optional<Member> findMember = memberRepository.findByNickname(nickname);
        if (findMember.isPresent()) {
            throw new DuplicateException("이미 사용중인 닉네임입니다.");
        }
    }

    /**
     * 회원 정보 검색
     * @return 회원 엔티티
     * @exception NoSuchException 등록되지 않은 회원의 PK로 조회하는 경우 예외 발생
     */
    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> {
            throw new NoSuchException("등록되지 않은 회원입니다.");
        });
    }
}
