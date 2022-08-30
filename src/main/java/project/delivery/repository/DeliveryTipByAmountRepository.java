package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.DeliveryTipByAmount;

import java.util.List;

public interface DeliveryTipByAmountRepository extends JpaRepository<DeliveryTipByAmount, Long> {

    @Query("select dtba from DeliveryTipByAmount dtba where dtba.deliveryInfo.id = :deliveryId order by dtba.deliveryTip asc")
    List<DeliveryTipByAmount> findAllByDeliveryId(@Param("deliveryId") Long deliveryId);
}
