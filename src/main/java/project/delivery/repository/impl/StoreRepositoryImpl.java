package project.delivery.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.delivery.domain.QStoreImage;
import project.delivery.domain.store.Category;
import project.delivery.domain.store.DeliveryInfo;
import project.delivery.domain.store.Store;
import project.delivery.repository.custom.StoreRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static project.delivery.domain.QDeliveryInfo.*;
import static project.delivery.domain.QDeliveryTipByAmount.*;
import static project.delivery.domain.QPackingInfo.*;
import static project.delivery.domain.QStore.*;
import static project.delivery.domain.QStoreImage.*;

public class StoreRepositoryImpl implements StoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public StoreRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Store> findStores(Category category) {
        return queryFactory
                .selectFrom(store)
                .join(store.deliveryInfo, deliveryInfo).fetchJoin()
                .leftJoin(store.packingInfo, packingInfo).fetchJoin()
                .where(store.category.eq(category))
                .fetch();
    }

    @Override
    public Store findStoreDetail(Long storeId) {
        return queryFactory
                .select(store).distinct()
                .from(store)
                .join(store.deliveryInfo, deliveryInfo).fetchJoin()
                .join(store.storeImages, storeImage).fetchJoin()
                .leftJoin(store.packingInfo, packingInfo).fetchJoin()
                .where(store.id.eq(storeId))
                .fetchOne();
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

    @Override
    public DeliveryInfo findDeliveryInfo(Long storeId) {
//        queryFactory
//                .select(deliveryInfo).distinct()
//                .from()
        return null;
    }
}
