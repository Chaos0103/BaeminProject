package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.pay.PayCard;
import project.delivery.repository.custom.PayCardRepositoryCustom;

import java.util.List;

public interface PayCardRepository extends JpaRepository<PayCard, Long>, PayCardRepositoryCustom {

    @Query("select pc from PayCard pc where pc.pay.id = :payId")
    List<PayCard> findPayCardPayId(@Param("payId") Long payId);
}
