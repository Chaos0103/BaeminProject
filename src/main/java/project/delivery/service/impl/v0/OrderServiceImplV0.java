package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.*;
import project.delivery.domain.basket.Basket;
import project.delivery.domain.basket.BasketMenu;
import project.delivery.domain.basket.BasketSubOptionInfo;
import project.delivery.domain.member.Member;
import project.delivery.domain.order.*;
import project.delivery.dto.OrderDto;
import project.delivery.dto.PaymentDto;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.*;
import project.delivery.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImplV0 implements OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final BasketRepository basketRepository;
    private final PointRepository pointRepository;
    private final CouponRepository couponRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public Long order(Long memberId, OrderDto orderDto, PaymentDto paymentDto) {
        //데이터 찾기
        Member member = findMember(memberId);
        Basket basket = findBasket(memberId);

        List<BasketMenu> basketMenus = basketRepository.findBasketMenus(basket.getId());
        List<MenuOrder> menuOrders = getMenuOrders(basketMenus);

        Payment payment;
        if (paymentDto.getCouponId() != null) {
            Coupon coupon = couponRepository.findById(paymentDto.getCouponId()).orElse(null);
            if (coupon == null) {
                throw new NoSuchException("존재하지 않는 쿠폰입니다");
            }
            //결제 생성
            payment = new Payment(paymentDto.getPaymentMethod(), paymentDto.getOrderAmount(), coupon.getCouponName(), coupon.getDiscountPrice(), paymentDto.getPoint(), paymentDto.getDeliveryTip(), paymentDto.getTotalAmount());
            coupon.changeStatus(CouponStatus.USE);
        } else {
            payment = new Payment(paymentDto.getPaymentMethod(), paymentDto.getOrderAmount(), null, null, paymentDto.getPoint(), paymentDto.getDeliveryTip(), paymentDto.getTotalAmount());
        }

        if (paymentDto.getPoint() != null) {
            Point point = pointRepository.findByMemberId(memberId).orElse(null);
            if (point == null) {
                throw new NoSuchException("포인트 내역을 찾을 수 없습니다");
            }
            PointHistory.createPointHistory(point, paymentDto.getPoint(), basket.getStore().getStoreName(), PointType.USE);
        }

        //주문 생성
        Order order = Order.createOrder(member, basket.getStore(), orderDto.getDelivery(), payment, getOrderNumber(), orderDto.getReceiptType(), orderDto.getDisposable(), orderDto.getSideDish(), orderDto.getRequirement(), menuOrders);

        Order savedOrder = orderRepository.save(order);
        basketRepository.delete(basket);

        notificationRepository.save(new Notification(member, basket.getStore().getStoreName(), NotificationType.DELIVERY));

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
    public void removeOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> findOrdersByMemberId(Long memberId) {
        return orderRepository.findOrdersByMemberId(memberId);
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

    private List<MenuOrder> getMenuOrders(List<BasketMenu> basketMenus) {
        List<MenuOrder> menuOrders = new ArrayList<>();
        for (BasketMenu basketMenu : basketMenus) {
            List<BasketSubOptionInfo> basketSubOptionInfos = basketMenu.getBasketSubOptionInfos();
            List<MenuSubOption> menuSubOptions = basketSubOptionInfos.stream()
                    .map(BasketSubOptionInfo::getMenuSubOption)
                    .toList();
            int orderPrice = getOrderPrice(basketMenu.getMenuOption(), menuSubOptions) * basketMenu.getCount();
            MenuOrder menuOrder = MenuOrder.createMenuOrder(basketMenu.getMenuOption(), basketMenu.getCount(), orderPrice, menuSubOptions);
            menuOrders.add(menuOrder);
        }
        return menuOrders;
    }

    private Integer getOrderPrice(MenuOption menuOption, List<MenuSubOption> menuSubOptions) {
        int orderPrice = menuOption.getPrice();
        for (MenuSubOption menuSubOption : menuSubOptions) {
            orderPrice += menuSubOption.getPrice();
        }
        return orderPrice;
    }

    private String getOrderNumber() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(uuid.length() - 10).toUpperCase();
    }
}
