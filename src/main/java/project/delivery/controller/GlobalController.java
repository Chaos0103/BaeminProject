package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import project.delivery.domain.member.Member;
import project.delivery.dto.BasketDto;
import project.delivery.dto.NotificationDto;
import project.delivery.service.query.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GlobalController {

    private final NotificationQueryService notificationQueryService;
    private final BasketQueryService basketQueryService;
    private final CouponQueryService couponQueryService;
    private final PayQueryService payQueryService;
    private final PointQueryService pointQueryService;

    public void headerInfo(Member loginMember, Model model) {
        List<NotificationDto> notifications = notificationQueryService.findNotifications(loginMember.getId());
        BasketDto basket = basketQueryService.findBasket(loginMember.getId());
        model.addAttribute("notifications", notifications);
        model.addAttribute("basket", basket);
        model.addAttribute("loginMember", loginMember);
    }

    public void topInfo(Member loginMember, Model model) {
        Map<String, Object> topInfoMap = new HashMap<>();
        Integer payMoney = payQueryService.findMoney(loginMember.getId());
        Integer countCoupon = couponQueryService.countAvailableCouponsByMemberId(loginMember.getId());
        Integer totalPoint = pointQueryService.findTotalPoint(loginMember.getId());

        topInfoMap.put("grade", loginMember.getGrade().getDescription());
        topInfoMap.put("payMoney", payMoney);
        topInfoMap.put("countCoupon", countCoupon);
        topInfoMap.put("totalPoint", totalPoint);

        model.addAttribute("topInfoMap", topInfoMap);
    }
}
