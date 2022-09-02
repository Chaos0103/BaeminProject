package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.delivery.domain.Member;
import project.delivery.login.Login;
import project.delivery.service.OrderService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members/orders")
public class OrderListController {

    private final OrderService orderService;

    @GetMapping
    public String orderHome(@Login Member loginMember) {

        return "member/orderList";
    }
}
