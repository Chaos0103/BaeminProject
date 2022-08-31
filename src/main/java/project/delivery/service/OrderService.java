package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.dto.OrderDto;

@Transactional(readOnly = true)
public interface OrderService {

    @Transactional
    Long order(Long memberId, OrderDto orderDto);

    @Transactional
    Long cancel(Long orderId);
}
