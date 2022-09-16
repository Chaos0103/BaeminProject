package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.delivery.controller.form.ReviewAddForm;
import project.delivery.domain.member.Review;
import project.delivery.domain.order.Order;
import project.delivery.dto.LoginMember;
import project.delivery.login.Login;
import project.delivery.service.*;
import project.delivery.service.query.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members/reviews")
public class ReviewController {

    private final GlobalInformation globalInformation;

    private final ReviewService reviewService;

    private final ReviewQueryService reviewQueryService;

    @GetMapping("/reviewable")
    public String reviewable(@Login LoginMember loginMember, Model model) {
        globalInformation.headerInfo(loginMember, model);
        globalInformation.topInfo(loginMember, model);
        List<Order> orders = reviewQueryService.findReviewableByMemberId(loginMember.getId());
        model.addAttribute("orders", orders);
        return "member/review/reviewable";
    }

    @PostMapping("/reviewable/{orderId}")
    public String write(ReviewAddForm form, @PathVariable Long orderId, @Login LoginMember loginMember) {
        Review review = new Review(null, null, form.getRating(), form.getContent(), null);
        Long reviewId = reviewService.writeReview(loginMember.getId(), orderId, review);
        log.debug("reviewId = {}", reviewId);
        return "redirect:/reviews/reviewable";
    }


    @GetMapping("/wroteReviews")
    public String wroteReviews(@Login LoginMember loginMember, Model model) {
        globalInformation.headerInfo(loginMember, model);
        globalInformation.topInfo(loginMember, model);
        List<Review> reviews = reviewQueryService.findWroteReviewsByMemberId(loginMember.getId());
        model.addAttribute("reviews", reviews);
        return "member/review/wroteReviews";
    }

    @ModelAttribute("reviewAddForm")
    public ReviewAddForm reviewAddForm() {
        return new ReviewAddForm();
    }
}
