package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.store.DeliveryTipByArea;

import java.util.List;

public interface DeliveryTipByAreaRepository extends JpaRepository<DeliveryTipByArea, Long> {

    @Query("select dtba from DeliveryTipByArea dtba where dtba.deliveryInfo.id = :deliveryId order by dtba.deliveryTip asc")
    List<DeliveryTipByArea> findAllByDeliveryId(@Param("deliveryId") Long deliveryId);
}
