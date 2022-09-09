package project.delivery.repository.custom;

import project.delivery.domain.Review;
import project.delivery.domain.order.Order;
import project.delivery.dto.ReviewSearch;

import java.util.List;

public interface ReviewRepositoryCustom {

    List<Review> findAllByStoreId(ReviewSearch search);

    List<Order> findReviewableByMemberId(Long memberId);

    List<Review> findWroteReviewsByMemberId(Long memberId);
}
