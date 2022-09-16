package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.dto.OrderDto;
import project.delivery.dto.PaymentDto;

@Transactional
public interface OrderService {

    Long order(Long memberId, OrderDto orderDto, PaymentDto paymentDto);

    Long cancel(Long orderId);

    void removeOrder(Long orderId);
}
