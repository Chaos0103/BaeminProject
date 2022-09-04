package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.Coupon;
import project.delivery.domain.CouponStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("select c from Coupon c where c.member.id = :memberId and c.lastModifiedDate >= :date")
    List<Coupon> findAllByMemberId(@Param("memberId") Long memberId, @Param("date") LocalDateTime date);

    @Query("select count(c) from Coupon c where c.member.id = :memberId and c.status = 'UNUSE'")
    Long countByMemberId(@Param("memberId") Long memberId);

    @Query("select count(c) from Coupon c where c.member.id = :memberId and c.expirationDate <= :date and c.status = 'UNUSE'")
    Long countDayByMemberId(@Param("memberId") Long memberId, @Param("date") LocalDateTime date);

    @Query("select c from Coupon c where c.member.id = :memberId and c.status = 'UNUSE'")
    List<Coupon> findCouponUse(@Param("memberId") Long memberId);
}
