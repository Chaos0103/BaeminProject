package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import project.delivery.domain.Member;
import project.delivery.domain.order.Order;
import project.delivery.dto.NotificationDto;
import project.delivery.login.Login;
import project.delivery.service.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members/orders")
public class OrderListController {

    private final OrderService orderService;
    private final PayService payService;
    private final CouponService couponService;
    private final PointService pointService;
    private final NotificationService notificationService;

    /**
     * @URL: localhost:8080/members/orders
     */
    @GetMapping
    public String orderListHome(@Login Member loginMember, Model model) {
        //주문 데이터 조회
        List<Order> orders = orderService.findOrdersByMemberId(loginMember.getId());
        //알림 조회
        List<NotificationDto> notifications = notificationService.findNotificationByMemberId(loginMember.getId());
        //페이머니 잔액 조회
        Integer money = payService.findMoney(loginMember.getId());
        //사용 가능한 쿠폰 갯수 조회
        Integer countCoupon = couponService.countCouponByMemberId(loginMember.getId());
        //포인트 잔액 조회
        Integer totalPoint = pointService.findTotalPoint(loginMember.getId());

        model.addAttribute("orders", orders);
        model.addAttribute("notifications", notifications);
        model.addAttribute("money", money);
        model.addAttribute("countCoupon", countCoupon);
        model.addAttribute("totalPoint", totalPoint);
        return "member/orderList";
    }

    @ModelAttribute("loginMember")
    public Member loginMember(@Login Member loginMember) {
        return loginMember;
    }
}
