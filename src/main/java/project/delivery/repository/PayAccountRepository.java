package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.delivery.domain.PayAccount;
import project.delivery.repository.custom.PayAccountRepositoryCustom;

public interface PayAccountRepository extends JpaRepository<PayAccount, Long>, PayAccountRepositoryCustom {
}
