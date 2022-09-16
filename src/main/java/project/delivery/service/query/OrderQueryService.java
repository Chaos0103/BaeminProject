package project.delivery.service.query;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.order.Order;

import java.util.List;

@Transactional(readOnly = true)
public interface OrderQueryService {

    List<Order> findOrdersByMemberId(Long memberId);
}
