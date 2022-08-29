package project.delivery.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.delivery.domain.*;
import project.delivery.repository.custom.StoreRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

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
}
