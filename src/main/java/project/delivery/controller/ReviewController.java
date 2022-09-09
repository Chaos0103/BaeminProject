package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.delivery.controller.form.ReviewAddForm;
import project.delivery.domain.Member;
import project.delivery.domain.Review;
import project.delivery.domain.order.Order;
import project.delivery.dto.BasketDto;
import project.delivery.dto.BasketMenuDto;
import project.delivery.dto.NotificationDto;
import project.delivery.login.Login;
import project.delivery.service.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final NotificationService notificationService;
    private final BasketService basketService;
    private final CouponService couponService;
    private final PointService pointService;
    private final PayService payService;

    private final ReviewService reviewService;

    @GetMapping("/reviewable")
    public String reviewable(@Login Member loginMember, Model model) {
        headDataInit(loginMember, model);
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
        headDataInit(loginMember, model);
        List<Review> reviews = reviewService.findWroteReviewsByMemberId(loginMember.getId());
        model.addAttribute("reviews", reviews);
        return "member/review/wroteReviews";
    }

    private void headDataInit(Member loginMember, Model model) {
        List<NotificationDto> notifications = notificationService.findNotificationByMemberId(loginMember.getId());
        List<BasketMenuDto> basketMenus = basketService.findAllByMemberId(loginMember.getId());
        BasketDto basket = basketService.findBasketDto(loginMember.getId());
        Integer countCoupon = couponService.countCouponByMemberId(loginMember.getId());
        Integer totalPoint = pointService.findTotalPoint(loginMember.getId());
        Integer money = payService.findMoney(loginMember.getId());

        model.addAttribute("notifications", notifications);
        model.addAttribute("basketMenus", basketMenus);
        model.addAttribute("basket", basket);
        model.addAttribute("countCoupon", countCoupon);
        model.addAttribute("totalPoint", totalPoint);
        model.addAttribute("money", money);
    }

    @ModelAttribute("loginMember")
    public Member loginMember(@Login Member loginMember) {
        return loginMember;
    }

    @ModelAttribute("reviewAddForm")
    public ReviewAddForm reviewAddForm() {
        return new ReviewAddForm();
    }
}
