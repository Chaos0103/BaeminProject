package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.delivery.domain.member.Member;
import project.delivery.dto.BasketDto;
import project.delivery.dto.NotificationDto;
import project.delivery.login.Login;
import project.delivery.service.query.NotificationQueryService;
import project.delivery.service.query.BasketQueryService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final NotificationQueryService notificationQueryService;
    private final BasketQueryService basketQueryService;

    @GetMapping
    public String mainHome(@Login Member loginMember, Model model) {

        if (loginMember == null) {
            return "home";
        }

        List<NotificationDto> notifications = notificationQueryService.findNotifications(loginMember.getId());
        BasketDto basket = basketQueryService.findBasket(loginMember.getId());

        model.addAttribute("notifications", notifications);
        model.addAttribute("basket", basket);
        return "loginHome";
    }
}
