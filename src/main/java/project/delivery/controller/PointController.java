package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.delivery.controller.form.VoucherSaveForm;
import project.delivery.domain.member.Member;
import project.delivery.domain.point.PointType;
import project.delivery.dto.*;
import project.delivery.exception.DuplicateException;
import project.delivery.exception.NoSuchException;
import project.delivery.login.Login;
import project.delivery.service.*;
import project.delivery.service.query.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members/points")
public class PointController {

    private final PointService pointService;

    private final NotificationQueryService notificationQueryService;
    private final BasketQueryService basketQueryService;
    private final CouponQueryService couponQueryService;
    private final PayQueryService payQueryService;
    private final PointQueryService pointQueryService;
    
    /**
     * @URL: localhost:8080/members/points
     */
    @GetMapping
    public String pointHome(
            @ModelAttribute("search") PointHistorySearch search,
            @Login Member loginMember, Model model) {
        headerInfo(loginMember, model);
        topInfo(loginMember, model);

        //포인트 데이터 조회
        PointDto point = pointQueryService.findPointByMemberId(loginMember.getId());
        //포인트 내역 조회
        List<PointHistoryDto> pointHistories = pointQueryService.findPointHistoryByPointId(point.getId(), search);

        model.addAttribute("point", point);
        model.addAttribute("pointHistories", pointHistories);

        model.addAttribute("voucherError", false);
        return "member/point";
    }

    /**
     * @URL: localhost:8080/points/addVoucher
     */
    @PostMapping("/addVoucher")
    public String addVoucher(
            @ModelAttribute("search") PointHistorySearch search,
            @Validated @ModelAttribute VoucherSaveForm form,
            BindingResult bindingResult,
            @Login Member loginMember, Model model) {

        try {
            Integer pointValue = pointService.voucherRegistration(loginMember.getId(), form.getVoucherCode());
            log.info("회원번호 {} 상품권 등록: {} 충전", loginMember.getId(), pointValue);
            return "redirect:/points";
        } catch (NoSuchException | DuplicateException e) {
            bindingResult.reject("voucherError", e.getMessage());
            model.addAttribute("voucherError", true);
            log.debug(e.getMessage());
            PointDto point = pointQueryService.findPointByMemberId(loginMember.getId());
            List<PointHistoryDto> pointHistories = pointQueryService.findPointHistoryByPointId(point.getId(), search);
            model.addAttribute("point", point);
            model.addAttribute("pointHistories", pointHistories);
            return "member/point";
        }
    }

    @ModelAttribute("loginMember")
    public Member loginMember(@Login Member loginMember) {
        return loginMember;
    }

    @ModelAttribute("pointTypes")
    public PointType[] pointTypes() {
        return PointType.values();
    }

    @ModelAttribute("voucherSaveForm")
    public VoucherSaveForm voucherSaveForm() {
        return new VoucherSaveForm();
    }

    private void headerInfo(Member loginMember, Model model) {
        //알림 조회
        List<NotificationDto> notifications = notificationQueryService.findNotifications(loginMember.getId());
        //장바구니 조회
        BasketDto basket = basketQueryService.findBasket(loginMember.getId());

        model.addAttribute("notifications", notifications);
        model.addAttribute("basket", basket);
    }

    private void topInfo(Member loginMember, Model model) {
        Map<String, Object> topInfoMap = new HashMap<>();
        //페이머니 잔액 조회
        Integer payMoney = payQueryService.findMoney(loginMember.getId());
        //사용 가능한 쿠폰 갯수 조회
        Integer countCoupon = couponQueryService.countAvailableCoupons(loginMember.getId());
        //포인트 잔액 조회
        Integer totalPoint = pointQueryService.findTotalPoint(loginMember.getId());

        topInfoMap.put("grade", loginMember.getGrade().getDescription());
        topInfoMap.put("payMoney", payMoney);
        topInfoMap.put("countCoupon", countCoupon);
        topInfoMap.put("totalPoint", totalPoint);

        model.addAttribute("topInfoMap", topInfoMap);
    }
}
