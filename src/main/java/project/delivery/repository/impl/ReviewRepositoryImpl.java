package project.delivery.repository.impl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import project.delivery.domain.QMember;
import project.delivery.domain.Review;
import project.delivery.dto.ReviewSearch;
import project.delivery.repository.custom.ReviewRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static project.delivery.domain.QMember.*;
import static project.delivery.domain.QReview.*;

public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ReviewRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Review> findAllByStoreId(ReviewSearch search) {
        return queryFactory
                .selectFrom(review)
                .join(review.member, member).fetchJoin()
                .where(
                        review.store.id.eq(search.getStoreId()),
                        isImage(search.getPhoto())
                )
                .orderBy(
                        isType(search.getType())
                )
                .fetch();
    }

    private BooleanExpression isImage(Boolean photo) {
        return photo ? review.uploadFile.isNotNull() : null;
    }

    private OrderSpecifier<?> isType(String type) {
        if (type.equals("highRating")) {
            return review.rating.desc();
        } else if (type.equals("lowRating")) {
            return review.rating.asc();
        } else {
            return review.lastModifiedDate.desc();
        }
    }
}
