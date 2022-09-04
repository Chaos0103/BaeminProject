package project.delivery.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.delivery.domain.*;
import project.delivery.repository.custom.StoreRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static project.delivery.domain.QDeliveryInfo.*;
import static project.delivery.domain.QDeliveryTipByAmount.*;
import static project.delivery.domain.QStore.*;

public class StoreRepositoryImpl implements StoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public StoreRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<Store> findAllByCondition(Category category) {
        return queryFactory
                .selectDistinct(store)
                .from(store)
                .where(
                        store.category.eq(category)
                )
                .fetch();
    }

    @Override
    public Integer findDeliveryTip(Long storeId, Integer totalAmount) {
        return queryFactory
                .select(deliveryTipByAmount.deliveryTip)
                .from(deliveryTipByAmount)
                .join(deliveryTipByAmount.deliveryInfo, deliveryInfo)
                .where(
                        deliveryTipByAmount.deliveryInfo.store.id.eq(storeId),
                        deliveryTipByAmount.minAmount.loe(totalAmount),
                        (deliveryTipByAmount.maxAmount.gt(totalAmount)
                                .or(deliveryTipByAmount.maxAmount.isNull()))
                )
                .fetchOne();
    }
}
