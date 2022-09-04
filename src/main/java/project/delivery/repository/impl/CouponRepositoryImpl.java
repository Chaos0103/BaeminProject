package project.delivery.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.delivery.dto.CouponDto;
import project.delivery.repository.custom.CouponRepositoryCustom;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.querydsl.core.types.Projections.*;
import static project.delivery.domain.QCoupon.*;

public class CouponRepositoryImpl implements CouponRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CouponRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<CouponDto> findCouponByMemberId(Long memberId, LocalDateTime period) {
        return queryFactory
                .select(fields(CouponDto.class,
                        coupon.couponName,
                        coupon.discountPrice,
                        coupon.expirationDate,
                        coupon.minOrderPrice,
                        coupon.status))
                .from(coupon)
                .where(
                        coupon.member.id.eq(memberId),
                        coupon.expirationDate.goe(period)
                )
                .fetch();
    }

}
