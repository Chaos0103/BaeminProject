package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.delivery.domain.PayCard;
import project.delivery.repository.custom.PayCardRepositoryCustom;

public interface PayCardRepository extends JpaRepository<PayCard, Long>, PayCardRepositoryCustom {
}
