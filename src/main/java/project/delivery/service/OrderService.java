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

    List<Order> findOrderList(Long memberId);
}
