package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.delivery.domain.member.Member;
import project.delivery.dto.BasketDto;
import project.delivery.dto.BasketMenuDto;
import project.delivery.dto.NotificationDto;
import project.delivery.login.Login;
import project.delivery.service.BasketService;
import project.delivery.service.NotificationService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final NotificationService notificationService;
    private final BasketService basketService;

    @GetMapping
    public String mainHome(@Login Member loginMember, Model model) {

        if (loginMember == null) {
            return "home";
        }

        List<NotificationDto> notifications = notificationService.findNotificationByMemberId(loginMember.getId());
        List<BasketMenuDto> basketMenus = basketService.findAllByMemberId(loginMember.getId());
        BasketDto basket = basketService.findBasketDto(loginMember.getId());

        model.addAttribute("notifications", notifications);
        model.addAttribute("basketMenus", basketMenus);
        model.addAttribute("basket", basket);
        return "loginHome";
    }
}
