package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.delivery.domain.Pay;

import java.util.Optional;

public interface PayRepository extends JpaRepository<Pay, Long> {
    Optional<Pay> findByMemberId(Long memberId);
}
