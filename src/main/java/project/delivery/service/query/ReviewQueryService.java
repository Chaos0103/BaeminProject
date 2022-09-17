package project.delivery.service.query;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.member.Review;
import project.delivery.domain.order.Order;
import project.delivery.dto.ReviewSearch;

import java.util.List;

@Transactional(readOnly = true)
public interface ReviewQueryService {

    List<Review> findAll(ReviewSearch search);

    List<Order> findReviewable(Long memberId);

    List<Review> findWroteReviews(Long memberId);
}
