package project.delivery.repository.custom;

import project.delivery.domain.order.Order;

import java.util.List;

public interface OrderRepositoryCustom {

    List<Order> findOrdersByMemberId(Long memberId);
}
