package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.delivery.domain.Member;
import project.delivery.domain.order.Order;
import project.delivery.dto.BasketDto;
import project.delivery.dto.BasketMenuDto;
import project.delivery.dto.NotificationDto;
import project.delivery.login.Login;
import project.delivery.service.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final BasketService basketService;

    /**
     * @URL: localhost:8080/members/orders
     */
    @GetMapping
    public String orderListHome(@Login Member loginMember, Model model) {
        headerInfo(loginMember, model);
        topInfo(loginMember, model);
        //주문 데이터 조회
        List<Order> orders = orderService.findOrdersByMemberId(loginMember.getId());
        model.addAttribute("orders", orders);
        return "member/orderList";
    }

    @PostMapping("/{orderId}/remove")
    public String removeOrder(@PathVariable Long orderId) {
        orderService.removeOrder(orderId);
        return "redirect:/members/orders";
    }


    @ModelAttribute("loginMember")
    public Member loginMember(@Login Member loginMember) {
        return loginMember;
    }

    private void headerInfo(Member loginMember, Model model) {
        //알림 조회
        List<NotificationDto> notifications = notificationService.findNotificationByMemberId(loginMember.getId());
        //장바구니 조회
        List<BasketMenuDto> basketMenus = basketService.findAllByMemberId(loginMember.getId());
        BasketDto basket = basketService.findBasketDto(loginMember.getId());

        model.addAttribute("notifications", notifications);
        model.addAttribute("basketMenus", basketMenus);
        model.addAttribute("basket", basket);
    }

    private void topInfo(Member loginMember, Model model) {
        Map<String, Object> topInfoMap = new HashMap<>();
        //페이머니 잔액 조회
        Integer payMoney = payService.findMoney(loginMember.getId());
        //사용 가능한 쿠폰 갯수 조회
        Integer countCoupon = couponService.countCouponByMemberId(loginMember.getId());
        //포인트 잔액 조회
        Integer totalPoint = pointService.findTotalPoint(loginMember.getId());

        topInfoMap.put("grade", loginMember.getGrade().getDescription());
        topInfoMap.put("payMoney", payMoney);
        topInfoMap.put("countCoupon", countCoupon);
        topInfoMap.put("totalPoint", totalPoint);

        model.addAttribute("topInfoMap", topInfoMap);
    }
}
