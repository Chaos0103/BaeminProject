package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.delivery.domain.PayAccount;

import java.util.List;

public interface PayAccountRepository extends JpaRepository<PayAccount, Long> {

    List<PayAccount> findByPayId(Long payId);
}
