package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.delivery.controller.form.ReviewAddForm;
import project.delivery.domain.member.Member;
import project.delivery.domain.member.Review;
import project.delivery.domain.order.Order;
import project.delivery.dto.BasketDto;
import project.delivery.dto.BasketMenuDto;
import project.delivery.dto.NotificationDto;
import project.delivery.login.Login;
import project.delivery.service.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members/reviews")
public class ReviewController {

    private final NotificationService notificationService;
    private final BasketService basketService;
    private final CouponService couponService;
    private final PointService pointService;
    private final PayService payService;

    private final ReviewService reviewService;

    @GetMapping("/reviewable")
    public String reviewable(@Login Member loginMember, Model model) {
        headerInfo(loginMember, model);
        topInfo(loginMember, model);
        List<Order> orders = reviewService.findReviewableByMemberId(loginMember.getId());
        model.addAttribute("orders", orders);
        return "member/review/reviewable";
    }

    @PostMapping("/reviewable/{orderId}")
    public String write(ReviewAddForm form, @PathVariable Long orderId, @Login Member loginMember) {
        Review review = new Review(null, null, form.getRating(), form.getContent(), null);
        Long reviewId = reviewService.writeReview(loginMember.getId(), orderId, review);
        log.debug("reviewId = {}", reviewId);
        return "redirect:/reviews/reviewable";
    }


    @GetMapping("/wroteReviews")
    public String wroteReviews(@Login Member loginMember, Model model) {
        headerInfo(loginMember, model);
        topInfo(loginMember, model);
        List<Review> reviews = reviewService.findWroteReviewsByMemberId(loginMember.getId());
        model.addAttribute("reviews", reviews);
        return "member/review/wroteReviews";
    }

    @ModelAttribute("loginMember")
    public Member loginMember(@Login Member loginMember) {
        return loginMember;
    }

    @ModelAttribute("reviewAddForm")
    public ReviewAddForm reviewAddForm() {
        return new ReviewAddForm();
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
        Integer countCoupon = couponService.countAvailableCouponsByMemberId(loginMember.getId());
        //포인트 잔액 조회
        Integer totalPoint = pointService.findTotalPoint(loginMember.getId());

        topInfoMap.put("grade", loginMember.getGrade().getDescription());
        topInfoMap.put("payMoney", payMoney);
        topInfoMap.put("countCoupon", countCoupon);
        topInfoMap.put("totalPoint", totalPoint);

        model.addAttribute("topInfoMap", topInfoMap);
    }
}
