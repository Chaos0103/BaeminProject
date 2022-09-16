package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.member.Review;

@Transactional
public interface ReviewService {

    Long writeReview(Long memberId, Long orderId, Review review);

    void removeReview(Long reviewId);

//    void editReview(Long review);
}
