package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.order.Order;
import project.delivery.dto.OrderDto;

import java.util.List;

@Transactional(readOnly = true)
public interface OrderService {

    @Transactional
    Long order(Long memberId, OrderDto orderDto);

    @Transactional
    Long cancel(Long orderId);

    List<Order> findOrderList(Long memberId);
}
