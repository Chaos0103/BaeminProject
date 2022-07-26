package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.pay.PayAccount;

import java.util.List;

public interface PayAccountRepository extends JpaRepository<PayAccount, Long> {

    @Query("select pa from PayAccount pa where pa.pay.id = :payId")
    List<PayAccount> findPayAccountByPayId(@Param("payId") Long payId);
}
