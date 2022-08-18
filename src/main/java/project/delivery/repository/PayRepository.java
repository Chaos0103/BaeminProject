package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.Pay;

import java.util.Optional;

public interface PayRepository extends JpaRepository<Pay, Long> {

    @Query("select p from Pay p where p.member.id = :memberId")
    Optional<Pay> findByMemberId(@Param("memberId") Long memberId);
}
