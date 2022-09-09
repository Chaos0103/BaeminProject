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
import project.delivery.dto.BasketDto;
import project.delivery.dto.BasketMenuDto;
import project.delivery.dto.CouponDto;
import project.delivery.dto.NotificationDto;
import project.delivery.exception.DuplicateException;
import project.delivery.exception.NoSuchException;
import project.delivery.login.Login;
import project.delivery.service.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members/coupons")
public class CouponController {

    private final CouponService couponService;
    private final NotificationService notificationService;
    private final PayService payService;
    private final PointService pointService;
    private final BasketService basketService;

    /**
     * @URL: localhost:8080/members/coupons
     */
    @GetMapping
    public String couponHome(@Login Member loginMember, Model model) {
        headerInfo(loginMember, model);
        topInfo(loginMember, model);

        List<CouponDto> coupons = couponService.findCouponByMemberId(loginMember.getId());
        Integer possibleCoupon = couponService.countCouponByMemberId(loginMember.getId());
        Long dayPossibleCoupon = couponService.countDayByMemberId(loginMember.getId());

        model.addAttribute("coupons", coupons);
        model.addAttribute("possibleCoupon", possibleCoupon);
        model.addAttribute("dayPossibleCoupon", dayPossibleCoupon);

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

    @ModelAttribute("couponSaveForm")
    public CouponSaveForm couponSaveForm() {
        return new CouponSaveForm();
    }

    @ModelAttribute("loginMember")
    public Member loginMember(@Login Member loginMember) {
        return loginMember;
    }

    private void headerInfo(Member loginMember, Model model) {
        //알림 조회
        List<NotificationDto> notifications = notificationService.findNotificationByMemberId(loginMember.getId());
        //장바구니 조회
        List<BasketMenuDto> basketMenus = basketService.findAllByMemberId(loginMember.getId());
        BasketDto basket = basketService.findBasketDto(loginMember.getId());

        model.addAttribute("notifications", notifications);
        model.addAttribute("basketMenus", basketMenus);
        model.addAttribute("basket", basket);
    }

    private void topInfo(Member loginMember, Model model) {
        Map<String, Object> topInfoMap = new HashMap<>();
        //페이머니 잔액 조회
        Integer payMoney = payService.findMoney(loginMember.getId());
        //사용 가능한 쿠폰 갯수 조회
        Integer countCoupon = couponService.countCouponByMemberId(loginMember.getId());
        //포인트 잔액 조회
        Integer totalPoint = pointService.findTotalPoint(loginMember.getId());

        topInfoMap.put("grade", loginMember.getGrade().getDescription());
        topInfoMap.put("payMoney", payMoney);
        topInfoMap.put("countCoupon", countCoupon);
        topInfoMap.put("totalPoint", totalPoint);

        model.addAttribute("topInfoMap", topInfoMap);
    }

    private static String makeCouponCode(CouponSaveForm form) {
        return form.getFirst() + form.getSecond() + form.getThird() + form.getFourth();
    }
}
