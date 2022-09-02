package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.order.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

//    @Query("select o from Order o join fetch o.")
//    List<Order> findOrderList(@Param("memberId") Long memberId);
}
