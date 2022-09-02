package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.basket.Basket;
import project.delivery.repository.custom.BasketRepositoryCustom;

public interface BasketRepository extends JpaRepository<Basket, Long>, BasketRepositoryCustom {

    @Query("select b from Basket b where b.member.id = :memberId")
    Basket findByMemberId(@Param("memberId") Long memberId);
}
