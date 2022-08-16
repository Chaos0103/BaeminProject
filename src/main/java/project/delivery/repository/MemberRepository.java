package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.delivery.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByPhone(String phone);

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByEmailAndPassword(String email, String password);

    Optional<Member> findByUsernameAndPhone(String username, String phone);

    Optional<Member> findByUsernameAndEmail(String username, String email);
}
