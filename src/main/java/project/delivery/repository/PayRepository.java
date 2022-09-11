package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.pay.Pay;

import java.util.Optional;

public interface PayRepository extends JpaRepository<Pay, Long> {

    @Query("select p from Pay p where p.member.id = :memberId")
    Optional<Pay> findByMemberId(@Param("memberId") Long memberId);

    @Query("select distinct p from Pay p left join fetch p.payHistories where p.member.id = :memberId")
    Optional<Pay> findPayByMemberId(@Param("memberId") Long memberId);

    @Query("select p.money from Pay p where p.member.id = :memberId")
    Integer findMoney(@Param("memberId") Long memberId);
}
