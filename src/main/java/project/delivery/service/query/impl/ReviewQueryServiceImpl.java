package project.delivery.service.query.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.member.Review;
import project.delivery.domain.order.Order;
import project.delivery.dto.ReviewSearch;
import project.delivery.repository.ReviewRepository;
import project.delivery.service.query.ReviewQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewRepository reviewRepository;

    @Override
    public List<Review> findAll(ReviewSearch search) {
        return reviewRepository.findAllByStoreId(search);
    }

    @Override
    public List<Order> findReviewable(Long memberId) {
        return reviewRepository.findReviewableByMemberId(memberId);
    }

    @Override
    public List<Review> findWroteReviews(Long memberId) {
        return reviewRepository.findWroteReviewsByMemberId(memberId);
    }
}
