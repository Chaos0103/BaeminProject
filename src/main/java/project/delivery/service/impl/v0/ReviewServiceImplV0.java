package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.Member;
import project.delivery.domain.Review;
import project.delivery.domain.Store;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.MemberRepository;
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

    @Override
    public Long writeReview(Long memberId, Long storeId, Review saveReview) {
        Member findMember = memberRepository.findById(memberId).orElse(null);
        if (findMember == null) {
            throw new NoSuchException("등록되지 않는 회원입니다");
        }
        Store findStore = storeRepository.findById(storeId).orElse(null);
        if (findStore == null) {
            throw new NoSuchException("등록되지 않는 매장입니다");
        }
        Review review = new Review(findMember, findStore, saveReview.getRating(), saveReview.getContent(), saveReview.getUploadFile());
        Review savedReview = reviewRepository.save(review);
        findStore.addReviewCount();
        return savedReview.getId();
    }

    @Override
    public void removeReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public List<Review> findAllByStoreId(Long storeId) {
        return reviewRepository.findAllByStoreId(storeId);
    }
}
