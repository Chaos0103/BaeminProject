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
import project.delivery.dto.CouponDto;
import project.delivery.dto.LoginMember;
import project.delivery.exception.DuplicateException;
import project.delivery.exception.NoSuchException;
import project.delivery.login.Login;
import project.delivery.service.*;
import project.delivery.service.query.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members/coupons")
public class CouponController {

    private final GlobalInformation globalInfo;

    private final CouponService couponService;
    private final CouponQueryService couponQueryService;

    /**
     * @URL: localhost:8080/members/coupons
     */
    @GetMapping
    public String couponHome(@Login LoginMember loginMember, Model model) {
        globalInfo.headerInfo(loginMember, model);
        globalInfo.topInfo(loginMember, model);

        List<CouponDto> coupons = couponQueryService.findCoupons(loginMember.getId());
        Integer possibleCoupon = couponQueryService.countAvailableCoupons(loginMember.getId());
        Integer dayPossibleCoupon = couponQueryService.countDayByMemberId(loginMember.getId());

        model.addAttribute("coupons", coupons);
        model.addAttribute("possibleCoupon", possibleCoupon);
        model.addAttribute("dayPossibleCoupon", dayPossibleCoupon);

        return "member/coupon";
    }

    /**
     * @URL: localhost:8080/members/coupons/addCoupon
     */
    @PostMapping("/addCoupon")
    public String addCoupon(
            @Validated @ModelAttribute CouponSaveForm form,
            BindingResult bindingResult,
            @Login LoginMember loginMember, Model model) {

        if (bindingResult.hasErrors()) {
            log.debug("CouponSaveForm 필드 에러 발생: {}개", bindingResult.getErrorCount());
            return "member/coupon";
        }

        String couponCode = makeCouponCode(form);
        log.debug("couponCode={}", couponCode);

        try {
            Long couponId = couponService.couponRegistration(loginMember.getId(), couponCode);
            log.debug("addCoupon Success: couponId={}", couponId);
        } catch (NoSuchException | DuplicateException e) {
            log.debug(e.getMessage());
            model.addAttribute("addCouponError", e.getMessage());
            return "member/coupon";
        }

        return "redirect:/coupons";
    }

    @ModelAttribute("couponSaveForm")
    public CouponSaveForm couponSaveForm() {
        return new CouponSaveForm();
    }

    private static String makeCouponCode(CouponSaveForm form) {
        return form.getFirst() + form.getSecond() + form.getThird() + form.getFourth();
    }
}
