package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.delivery.domain.order.Order;
import project.delivery.dto.LoginMember;
import project.delivery.login.Login;
import project.delivery.service.*;
import project.delivery.service.query.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members/orders")
public class OrderListController {

    private final GlobalInformation globalInformation;

    private final OrderService orderService;
    private final OrderQueryService orderQueryService;

    /**
     * @URL: localhost:8080/members/orders
     */
    @GetMapping
    public String orderListHome(@Login LoginMember loginMember, Model model) {
        globalInformation.headerInfo(loginMember, model);
        globalInformation.topInfo(loginMember, model);
        //주문 데이터 조회
        List<Order> orders = orderQueryService.findOrdersByMemberId(loginMember.getId());
        model.addAttribute("orders", orders);
        return "member/orderList";
    }

    @PostMapping("/{orderId}/remove")
    public String removeOrder(@PathVariable Long orderId) {
        orderService.removeOrder(orderId);
        return "redirect:/members/orders";
    }
}
