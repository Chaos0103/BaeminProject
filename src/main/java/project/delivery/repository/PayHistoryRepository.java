package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.delivery.domain.PayHistory;
import project.delivery.repository.custom.PayHistoryRepositoryCustom;

public interface PayHistoryRepository extends JpaRepository<PayHistory, Long>, PayHistoryRepositoryCustom {
}
