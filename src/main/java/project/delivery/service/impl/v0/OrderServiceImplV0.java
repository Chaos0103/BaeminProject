package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.*;
import project.delivery.domain.basket.Basket;
import project.delivery.domain.basket.BasketMenu;
import project.delivery.domain.basket.BasketSubOptionInfo;
import project.delivery.domain.order.*;
import project.delivery.dto.OrderDto;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.*;
import project.delivery.service.NotificationService;
import project.delivery.service.OrderService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImplV0 implements OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final BasketRepository basketRepository;
    private final NotificationService notificationService;

    @Override
    public Long order(Long memberId, OrderDto orderDto) {
        //데이터 찾기
        Member member = findMember(memberId);
        Basket basket = findBasket(memberId);
        List<BasketMenu> basketMenus = basketRepository.findBasketMenus(basket.getId());

        List<MenuOrder> menuOrders = new ArrayList<>();

        for (BasketMenu basketMenu : basketMenus) {
            List<BasketSubOptionInfo> basketSubOptionInfos = basketMenu.getBasketSubOptionInfos();
            List<MenuSubOption> menuSubOptions = basketSubOptionInfos.stream()
                    .map(BasketSubOptionInfo::getMenuSubOption)
                    .toList();
            Integer orderPrice = getOrderPrice(basketMenu.getMenuOption(), menuSubOptions);
            MenuOrder menuOrder = MenuOrder.createMenuOrder(basketMenu.getMenuOption(), basketMenu.getCount(), orderPrice, menuSubOptions);
            menuOrders.add(menuOrder);
        }

        //주문 생성
        Order order = Order.createOrder(member, basket.getStore(), orderDto.getDelivery(), orderDto.getReceiptType(), orderDto.getDisposable(), orderDto.getSideDish(), orderDto.getRequirement(), menuOrders);

        Order savedOrder = orderRepository.save(order);
        basketRepository.delete(basket);

        Notification notification = new Notification(basket.getStore().getStoreName(), "", NotificationType.DELIVERY);
        Long notificationId = notificationService.createNotification(memberId, notification);

        return savedOrder.getId();
    }

    @Override
    public Long cancel(Long orderId) {
        Order order = findOrder(orderId);
        if (order.getStatus() != OrderStatus.ORDER) {
            throw new IllegalArgumentException("주문 취소 불가");
        }
        orderRepository.delete(order);
        return orderId;
    }

    @Override
    public List<Order> findOrderList(Long memberId) {
        return null;
    }

    private Integer getOrderPrice(MenuOption menuOption, List<MenuSubOption> menuSubOptions) {
        int orderPrice = menuOption.getPrice();
        for (MenuSubOption menuSubOption : menuSubOptions) {
            orderPrice += menuSubOption.getPrice();
        }
        return orderPrice;
    }

    private Order findOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            throw new NoSuchException("주문 내역이 없습니다");
        }
        return order;
    }

    private Member findMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member == null) {
            throw new NoSuchException("등록되지 않은 회원입니다");
        }
        return member;
    }

    private Basket findBasket(Long memberId) {
        return basketRepository.findByMemberId(memberId);
    }
}
