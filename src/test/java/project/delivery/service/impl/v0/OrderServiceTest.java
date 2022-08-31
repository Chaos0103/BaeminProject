package project.delivery.service.impl.v0;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.Address;
import project.delivery.domain.Member;
import project.delivery.domain.MenuOption;
import project.delivery.domain.order.Delivery;
import project.delivery.domain.order.DeliveryStatus;
import project.delivery.domain.order.Order;
import project.delivery.domain.order.ReceiptType;
import project.delivery.dto.OrderDto;
import project.delivery.dto.OrderInfoDto;
import project.delivery.repository.MemberRepository;
import project.delivery.repository.MenuOptionRepository;
import project.delivery.repository.OrderRepository;
import project.delivery.service.OrderService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired MenuOptionRepository menuOptionRepository;

    @Test
    @DisplayName("주문")
    void order() {
        //Member 생성
        Member member = createMember();
        //MenuOption 생성
        MenuOption menuOption = new MenuOption(null, "menuOption", 10000);
        menuOptionRepository.save(menuOption);
        //Delivery 생성
        Delivery delivery = new Delivery(new Address("12345", "test", "test"), "010-1234-5678", "010-1234-5678", "조심히 안전하게 와주세요 :)");
        //OrderDto 생성
        List<OrderInfoDto> orderInfos = new ArrayList<>();
        orderInfos.add(new OrderInfoDto(menuOption.getId(), null, 1));
        OrderDto orderDto = new OrderDto(delivery, ReceiptType.DELIVERY, false, false, null, orderInfos);


        Long orderId = orderService.order(member.getId(), orderDto);

        Order order = orderRepository.findById(orderId).get();
        assertThat(order.getReceiptType()).isEqualTo(ReceiptType.DELIVERY);
        assertEquals(order.getDelivery(), delivery);
        assertThat(order.getDelivery().getStatus()).isEqualTo(DeliveryStatus.COMP);
        assertThat(order.getMenuOrders().get(0).getOrderPrice()).isEqualTo(10000);
        assertThat(order.getMenuOrders().get(0).getMenuOption().getId()).isEqualTo(menuOption.getId());
        assertThat(order.getMenuOrders().get(0).getSubOptionInfos().size()).isEqualTo(0);
    }

    private Member createMember() {
        Address address = new Address("12345", "mainAddress", "detailAddress");
        Member member = new Member("test@test.com", "test1!", "user", "20010101", "01011111111", "tester", address);
        return memberRepository.save(member);
    }
}