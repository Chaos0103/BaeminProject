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
import project.delivery.controller.form.CouponSaveForm;
import project.delivery.domain.Coupon;
import project.delivery.domain.Member;
import project.delivery.dto.CouponDto;
import project.delivery.dto.NotificationDto;
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
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;
    private final NotificationService notificationService;
    private final PayService payService;
    private final PointService pointService;

    @ModelAttribute("couponSaveForm")
    public CouponSaveForm couponSaveForm() {
        return new CouponSaveForm();
    }

    @ModelAttribute("loginMember")
    public Member loginMember(@Login Member loginMember) {
        return loginMember;
    }

    @GetMapping
    public String couponHome(@Login Member loginMember, Model model) {
        List<NotificationDto> notifications = notificationService.findNotificationByMemberId(loginMember.getId());
        List<CouponDto> coupons = couponService.findCouponByMemberId(loginMember.getId());
        Integer possibleCoupon = couponService.countCouponByMemberId(loginMember.getId());
        Long dayPossibleCoupon = couponService.countDayByMemberId(loginMember.getId());
        //페이머니 잔액 조회
        Integer money = payService.findMoney(loginMember.getId());
        //포인트 잔액 조회
        Integer totalPoint = pointService.findTotalPoint(loginMember.getId());

        model.addAttribute("notifications", notifications);
        model.addAttribute("coupons", coupons);
        model.addAttribute("possibleCoupon", possibleCoupon);
        model.addAttribute("dayPossibleCoupon", dayPossibleCoupon);
        model.addAttribute("money", money);
        model.addAttribute("totalPoint", totalPoint);

        return "member/coupon";
    }

    @PostMapping("/addCoupon")
    public String addCoupon(
            @Validated @ModelAttribute CouponSaveForm form,
            BindingResult bindingResult,
            @Login Member loginMember, Model model) {

        if (bindingResult.hasErrors()) {
            log.debug("필드 에러 발생: {}개", bindingResult.getErrorCount());
            return "member/coupon";
        }

        String couponCode = makeCouponCode(form);
        log.debug("couponCode={}", couponCode);

        try {
            Long couponId = couponService.addCoupon(loginMember, couponCode);
        } catch (NoSuchException | DuplicateException e) {
            log.debug(e.getMessage());
            model.addAttribute("addCouponError", e.getMessage());
            return "member/coupon";
        }

        return "redirect:/coupons";
    }

    private static String makeCouponCode(CouponSaveForm form) {
        return form.getFirst() + form.getSecond() + form.getThird() + form.getFourth();
    }
}
