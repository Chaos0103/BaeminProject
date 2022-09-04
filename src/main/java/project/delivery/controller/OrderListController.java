package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import project.delivery.domain.Member;
import project.delivery.domain.Notification;
import project.delivery.domain.order.Order;
import project.delivery.dto.NotificationDto;
import project.delivery.login.Login;
import project.delivery.service.NotificationService;
import project.delivery.service.OrderService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members/orders")
public class OrderListController {

    private final OrderService orderService;
    private final NotificationService notificationService;

    @ModelAttribute("loginMember")
    public Member loginMember(@Login Member loginMember) {
        return loginMember;
    }

    @GetMapping
    public String orderHome(@Login Member loginMember, Model model) {
        List<NotificationDto> notifications = notificationService.findNotificationByMemberId(loginMember.getId());
        List<Order> orders = orderService.findOrderList(loginMember.getId());
        model.addAttribute("notifications", notifications);
        model.addAttribute("orders", orders);
        return "member/orderList";
    }
}
