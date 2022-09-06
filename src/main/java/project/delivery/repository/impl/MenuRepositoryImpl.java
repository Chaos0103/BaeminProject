package project.delivery.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.delivery.repository.custom.MenuRepositoryCustom;

import javax.persistence.EntityManager;

public class MenuRepositoryImpl implements MenuRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MenuRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

}
