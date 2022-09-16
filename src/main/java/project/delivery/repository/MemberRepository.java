package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.member.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findEmailByPhone(String phone);

    Optional<Member> findByNickname(String nickname);

    @Query("select m from Member m where m.email = :email and m.password = :password")
    Optional<Member> login(@Param("email") String email,@Param("password") String password);

    Optional<Member> findMemberIdByEmailAndPhone(String email, String phone);

    @Query("select m from Member m where m.id = :memberId")
    Member findLoginMember(@Param("memberId") Long memberId);
}
