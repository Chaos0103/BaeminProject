package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.delivery.domain.PayCard;

import java.util.List;

public interface PayCardRepository extends JpaRepository<PayCard, Long> {

    List<PayCard> findByPayId(Long payId);
}
