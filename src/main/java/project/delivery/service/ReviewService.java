package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.member.Review;
import project.delivery.domain.order.Order;
import project.delivery.dto.ReviewSearch;

import java.util.List;

@Transactional(readOnly = true)
public interface ReviewService {
    @Transactional
    Long writeReview(Long memberId, Long orderId, Review review);

    @Transactional
    void removeReview(Long reviewId);

//    @Transactional
//    void editReview(Long review);

    List<Review> findAllByStoreId(ReviewSearch search);

    List<Order> findReviewableByMemberId(Long memberId);

    List<Review> findWroteReviewsByMemberId(Long memberId);
}
