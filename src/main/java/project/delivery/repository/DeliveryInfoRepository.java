package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.DeliveryInfo;

public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo, Long> {

    @Query("select di from DeliveryInfo di where di.store.id = :storeId")
    DeliveryInfo findByStoreId(@Param("storeId") Long storeId);
}
