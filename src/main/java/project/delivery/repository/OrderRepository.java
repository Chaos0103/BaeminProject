package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.delivery.domain.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
