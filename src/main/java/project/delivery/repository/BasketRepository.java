package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.basket.Basket;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Long> {

    @Query("select distinct b from Basket b " +
            "join fetch b.store " +
            "join fetch b.menuOption mo " +
            "join fetch mo.menu m " +
            "left join fetch b.basketSubOptionInfos bsoi " +
            "left join fetch bsoi.menuSubOption " +
            "where b.member.id = :memberId")
    List<Basket> findAllByMemberId(@Param("memberId") Long memberId);
}
