package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.order.Order;
import project.delivery.dto.OrderDto;
import project.delivery.dto.PaymentDto;

import java.util.List;

@Transactional(readOnly = true)
public interface OrderService {

    @Transactional
    Long order(Long memberId, OrderDto orderDto, PaymentDto paymentDto);

    @Transactional
    Long cancel(Long orderId);

    @Transactional
    void removeOrder(Long orderId);

    List<Order> findOrdersByMemberId(Long memberId);
}
