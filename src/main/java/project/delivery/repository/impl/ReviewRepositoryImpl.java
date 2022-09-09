package project.delivery.repository.impl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import project.delivery.domain.*;
import project.delivery.domain.order.*;
import project.delivery.dto.ReviewSearch;
import project.delivery.repository.custom.ReviewRepositoryCustom;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static project.delivery.domain.QDeliveryInfo.*;
import static project.delivery.domain.QMember.*;
import static project.delivery.domain.QPackingInfo.*;
import static project.delivery.domain.QReview.*;
import static project.delivery.domain.QStore.*;
import static project.delivery.domain.order.QDelivery.*;
import static project.delivery.domain.order.QOrder.*;

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
                .join(review.order, order).fetchJoin()
                .where(
                        order.store.id.eq(search.getStoreId()),
                        isImage(search.getPhoto())
                )
                .orderBy(
                        isType(search.getType())
                )
                .fetch();
    }

    @Override
    public List<Order> findReviewableByMemberId(Long memberId) {
        LocalDateTime period = LocalDateTime.now().minusDays(3);
        return queryFactory
                .selectFrom(order)
                .join(order.store, store).fetchJoin()
                .join(order.delivery, delivery).fetchJoin()
                .join(store.deliveryInfo, deliveryInfo).fetchJoin()
                .join(store.packingInfo, packingInfo).fetchJoin()
                .where(
                        order.member.id.eq(memberId),
                        order.lastModifiedDate.goe(period),
                        order.reviewCreationStatus.isFalse(),
                        order.status.eq(OrderStatus.COMP),
                        delivery.status.eq(DeliveryStatus.COMP)
                )
                .fetch();
    }

    @Override
    public List<Review> findWroteReviewsByMemberId(Long memberId) {
        return queryFactory
                .selectFrom(review)
                .join(review.order, order).fetchJoin()
                .join(order.store, store).fetchJoin()
                .where(review.member.id.eq(memberId))
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
