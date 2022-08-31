package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.Member;
import project.delivery.domain.MenuOption;
import project.delivery.domain.MenuSubOption;
import project.delivery.domain.order.*;
import project.delivery.dto.OrderDto;
import project.delivery.dto.OrderInfoDto;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.MemberRepository;
import project.delivery.repository.MenuOptionRepository;
import project.delivery.repository.MenuSubOptionRepository;
import project.delivery.repository.OrderRepository;
import project.delivery.service.OrderService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImplV0 implements OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final MenuSubOptionRepository menuSubOptionRepository;

    @Override
    public Long order(Long memberId, OrderDto orderDto) {
        //데이터 찾기
        Member member = findMember(memberId);
        List<MenuOrder> menuOrders = new ArrayList<>();
        for (OrderInfoDto orderInfo : orderDto.getOrderInfos()) {
            MenuOption menuOption = findMenuOption(orderInfo.getMenuOptionId());
            List<MenuSubOption> menuSubOptions = findMenuSubOptions(orderInfo.getMenuSubOptionIds());
            Integer orderPrice = getOrderPrice(menuOption, menuSubOptions);
            //메뉴 주문 생성
            MenuOrder menuOrder = MenuOrder.createMenuOrder(menuOption, orderInfo.getCount(), orderPrice, menuSubOptions);
            menuOrders.add(menuOrder);
        }

        //주문 생성
        Order order = Order.createOrder(member, orderDto.getDelivery(), orderDto.getReceiptType(), orderDto.getDisposable(), orderDto.getSideDish(), orderDto.getRequirement(), menuOrders);

        Order savedOrder = orderRepository.save(order);

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

    private MenuOption findMenuOption(Long menuOptionId) {
        MenuOption menuOption = menuOptionRepository.findById(menuOptionId).orElse(null);
        if (menuOption == null) {
            throw new NoSuchException("등록되지 않은 메뉴입니다");
        }
        return menuOption;
    }

    private List<MenuSubOption> findMenuSubOptions(List<Long> menuSubOptionIds) {
        return menuSubOptionRepository.findAllByIds(menuSubOptionIds);
    }
}
