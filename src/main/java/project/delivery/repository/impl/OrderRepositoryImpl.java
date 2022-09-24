package project.delivery.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.delivery.domain.order.Order;
import project.delivery.repository.custom.OrderRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static project.delivery.domain.store.QDeliveryInfo.*;
import static project.delivery.domain.store.QMenu.*;
import static project.delivery.domain.store.QMenuOption.*;
import static project.delivery.domain.store.QPackingInfo.*;
import static project.delivery.domain.store.QStore.*;
import static project.delivery.domain.order.QDelivery.*;
import static project.delivery.domain.order.QMenuOrder.*;
import static project.delivery.domain.order.QOrder.*;
import static project.delivery.domain.order.QPayment.*;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Order> findOrdersByMemberId(Long memberId) {
        return queryFactory
                .select(order)
                .from(order)
                .join(order.store, store).fetchJoin()
                .join(order.payment, payment).fetchJoin()
                .join(order.delivery, delivery).fetchJoin()
                .join(order.menuOrders, menuOrder).fetchJoin()
                .join(menuOrder.menuOption, menuOption).fetchJoin()
                .join(menuOption.menu, menu).fetchJoin()
                .join(store.deliveryInfo, deliveryInfo).fetchJoin()
                .leftJoin(store.packingInfo, packingInfo).fetchJoin()
                .where(order.member.id.eq(memberId))
                .fetch();
    }
}
