package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.delivery.domain.Member;
import project.delivery.domain.Notification;
import project.delivery.login.Login;
import project.delivery.service.NotificationService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final NotificationService notificationService;

    @GetMapping
    public String mainHome(@Login Member loginMember, Model model) {

        if (loginMember == null) {
            return "home";
        }

        List<Notification> notifications = notificationService.findByMemberId(loginMember.getId());
        model.addAttribute("notifications", notifications);

        return "loginHome";
    }
}
