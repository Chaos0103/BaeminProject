package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import project.delivery.domain.*;
import project.delivery.login.Login;
import project.delivery.service.NotificationService;
import project.delivery.service.PointService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/points")
public class PointController {

    private final PointService pointService;
    private final NotificationService notificationService;

    @ModelAttribute("notifications")
    public List<Notification> notifications(@Login Member loginMember) {
        return notificationService.findByMemberId(loginMember.getId());
    }

    @GetMapping
    public String pointHome(HttpSession session, Model model) {
        Member loginMember = getLoginMember(session);

        Point point = pointService.getPointByMember(loginMember.getId());
        List<PointHistory> pointHistories = pointService.searchPoint(point.getId(), PointType.USE, 1);
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("point", point);
        model.addAttribute("pointHistories", pointHistories);
        return "member/point";
    }

    private Member getLoginMember(HttpSession session) {
        return (Member) session.getAttribute("loginMember");
    }
}
