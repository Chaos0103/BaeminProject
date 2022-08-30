package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.delivery.domain.PackingInfo;

public interface PackingInfoRepository extends JpaRepository<PackingInfo, Long> {
}
