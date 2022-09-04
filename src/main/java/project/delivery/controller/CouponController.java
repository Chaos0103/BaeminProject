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
import project.delivery.domain.Notification;
import project.delivery.dto.NotificationDto;
import project.delivery.exception.DuplicateException;
import project.delivery.exception.NoSuchException;
import project.delivery.login.Login;
import project.delivery.service.CouponService;
import project.delivery.service.NotificationService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;
    private final NotificationService notificationService;

    @ModelAttribute("notifications")
    public List<NotificationDto> notifications(@Login Member loginMember) {
        return notificationService.findNotificationByMemberId(loginMember.getId());
    }

    @ModelAttribute("loginMember")
    public Member loginMember(@Login Member loginMember) {
        return loginMember;
    }

    @ModelAttribute("coupons")
    public List<Coupon> coupons(@Login Member loginMember) {
        return couponService.findCouponAll(loginMember.getId());
    }

    @ModelAttribute("possibleCoupon")
    public Long possibleCoupon(@Login Member loginMember) {
        return couponService.countByMemberId(loginMember.getId());
    }

    @ModelAttribute("dayPossibleCoupon")
    public Long dayPossibleCoupon(@Login Member loginMember) {
        return couponService.countDayByMemberId(loginMember.getId());
    }

    @ModelAttribute("couponSaveForm")
    public CouponSaveForm couponSaveForm() {
        return new CouponSaveForm();
    }

    @GetMapping
    public String couponHome() {
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
