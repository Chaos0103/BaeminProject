package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.Address;
import project.delivery.domain.Member;
import project.delivery.dto.AddressDto;
import project.delivery.dto.MemberDto;
import project.delivery.exception.DuplicateException;
import project.delivery.repository.MemberRepository;
import project.delivery.service.MemberService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImplV0 implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Long joinMember(MemberDto memberDto) {
        duplicatedEmail(memberDto.getEmail());
        duplicatedPhone(memberDto.getPhone());
        duplicatedNickname(memberDto.getNickname());

        Member member = createMember(memberDto);
        Member savedMember = memberRepository.save(member);

        return savedMember.getId();
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
        Optional<Member> findMember = memberRepository.findByPhone(phone);
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
     * 회원 엔티티 생성
     * @return 회원 엔티티
     */
    private static Member createMember(MemberDto memberDto) {
        Address address = createAddress(memberDto.getAddressDto());
        return new Member(memberDto.getEmail(), memberDto.getPassword(), memberDto.getUsername(), memberDto.getBirth(), memberDto.getPhone(), memberDto.getNickname(), address);
    }

    /**
     * 주소 값타입 생성
     * @return 주소 값타입
     */
    private static Address createAddress(AddressDto addressDto) {
        return new Address(addressDto.getZipcode(), addressDto.getMainAddress(), addressDto.getDetailAddress());
    }
}
