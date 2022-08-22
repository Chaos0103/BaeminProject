package project.delivery.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.delivery.domain.Category;
import project.delivery.domain.QDeliveryInfo;
import project.delivery.domain.QStore;
import project.delivery.domain.Store;
import project.delivery.repository.custom.StoreRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static project.delivery.domain.QDeliveryInfo.*;
import static project.delivery.domain.QStore.*;

public class StoreRepositoryImpl implements StoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public StoreRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<Store> findAllByCondition(Category category) {
        return queryFactory
                .selectFrom(store)
                .join(store.deliveryInfo, deliveryInfo)
                .fetchJoin()
                .where(
                        store.category.eq(category)
                )
                .fetch();
    }
}
