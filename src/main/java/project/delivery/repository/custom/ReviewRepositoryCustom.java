package project.delivery.repository.custom;

import project.delivery.domain.Review;
import project.delivery.dto.ReviewSearch;

import java.util.List;

public interface ReviewRepositoryCustom {

    List<Review> findAllByStoreId(ReviewSearch search);
}
