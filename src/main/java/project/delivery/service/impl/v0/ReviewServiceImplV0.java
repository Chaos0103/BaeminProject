package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.member.Member;
import project.delivery.domain.member.Review;
import project.delivery.domain.store.Store;
import project.delivery.domain.order.Order;
import project.delivery.dto.ReviewSearch;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.MemberRepository;
import project.delivery.repository.OrderRepository;
import project.delivery.repository.ReviewRepository;
import project.delivery.repository.StoreRepository;
import project.delivery.service.ReviewService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImplV0 implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;

    @Override
    public Long writeReview(Long memberId, Long orderId, Review saveReview) {
        Member findMember = memberRepository.findById(memberId).orElse(null);
        if (findMember == null) {
            throw new NoSuchException("등록되지 않는 회원입니다");
        }
        Order findOrder = orderRepository.findById(orderId).orElse(null);
        if (findOrder == null) {
            throw new NoSuchException("등록되지 않은 주문입니다");
        }
        Store findStore = storeRepository.findById(findOrder.getStore().getId()).orElse(null);
        if (findStore == null) {
            throw new NoSuchException("등록되지 않는 매장입니다");
        }

        Review review = new Review(findMember, findOrder, saveReview.getRating(), saveReview.getContent(), saveReview.getUploadFile());
        Review savedReview = reviewRepository.save(review);

        findStore.addReviewCount();
        findOrder.changeReviewCreationStatus();
//        Float avg = reviewRepository.ratingAvgByStoreId(storeId);
//        findStore.updateRating(avg);

        return savedReview.getId();
    }

    @Override
    public void removeReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public List<Review> findAllByStoreId(ReviewSearch search) {
        return reviewRepository.findAllByStoreId(search);
    }

    @Override
    public List<Order> findReviewableByMemberId(Long memberId) {
        return reviewRepository.findReviewableByMemberId(memberId);
    }

    @Override
    public List<Review> findWroteReviewsByMemberId(Long memberId) {
        return reviewRepository.findWroteReviewsByMemberId(memberId);
    }
}
