package project.delivery.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.delivery.dto.PayCardDto;
import project.delivery.repository.custom.PayCardRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static com.querydsl.core.types.Projections.*;
import static project.delivery.domain.QPayCard.*;

public class PayCardRepositoryImpl implements PayCardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PayCardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
}
