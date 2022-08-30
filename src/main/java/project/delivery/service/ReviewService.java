package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.Review;

import java.util.List;

@Transactional(readOnly = true)
public interface ReviewService {
    @Transactional
    Long writeReview(Long memberId, Long storeId, Review review);

    @Transactional
    void removeReview(Long reviewId);

//    @Transactional
//    void editReview(Long review);

    List<Review> findAllByStoreId(Long storeId);
}
