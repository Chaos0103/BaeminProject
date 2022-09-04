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
import project.delivery.domain.*;
import project.delivery.dto.NotificationDto;
import project.delivery.dto.PointDto;
import project.delivery.dto.PointHistoryDto;
import project.delivery.dto.PointHistorySearch;
import project.delivery.exception.DuplicateException;
import project.delivery.exception.NoSuchException;
import project.delivery.login.Login;
import project.delivery.service.CouponService;
import project.delivery.service.NotificationService;
import project.delivery.service.PayService;
import project.delivery.service.PointService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/points")
public class PointController {

    private final PointService pointService;
    private final PayService payService;
    private final CouponService couponService;
    private final NotificationService notificationService;

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

    /**
     * @URL: localhost:8080/points
     */
    @GetMapping
    public String pointHome(
            @ModelAttribute("search") PointHistorySearch search,
            @Login Member loginMember, Model model) {

        //알림 조회
        List<NotificationDto> notifications = notificationService.findNotificationByMemberId(loginMember.getId());
        //포인트 데이터 조회
        PointDto point = pointService.findPointByMemberId(loginMember.getId());
        //포인트 내역 조회
        List<PointHistoryDto> pointHistories = pointService.findPointHistoryByPointId(point.getId(), search);
        //페이머니 잔액 조회
        Integer money = payService.findMoney(loginMember.getId());
        //사용 가능한 쿠폰 갯수 조회
        Integer countCoupon = couponService.countCouponByMemberId(loginMember.getId());

        model.addAttribute("notifications", notifications);
        model.addAttribute("point", point);
        model.addAttribute("pointHistories", pointHistories);
        model.addAttribute("money", money);
        model.addAttribute("countCoupon", countCoupon);

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
            Integer pointValue = pointService.addVoucher(loginMember.getId(), form.getVoucherCode());
            log.info("회원번호 {} 상품권 등록: {} 충전", loginMember.getId(), pointValue);
            return "redirect:/points";
        } catch (NoSuchException | DuplicateException e) {
            bindingResult.reject("voucherError", e.getMessage());
            model.addAttribute("voucherError", true);
            log.debug(e.getMessage());
            PointDto point = pointService.findPointByMemberId(loginMember.getId());
            List<PointHistoryDto> pointHistories = pointService.findPointHistoryByPointId(point.getId(), search);
            model.addAttribute("point", point);
            model.addAttribute("pointHistories", pointHistories);
            return "member/point";
        }
    }
}
