package project.delivery.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import project.delivery.domain.PointHistory;
import project.delivery.domain.PointType;
import project.delivery.domain.QPointHistory;
import project.delivery.repository.custom.PointRepositoryCustom;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static project.delivery.domain.QPointHistory.*;

public class PointRepositoryImpl implements PointRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PointRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<PointHistory> findHistory(Long pointId, PointType type, LocalDateTime date) {
        return queryFactory
                .selectFrom(pointHistory)
                .where(
                        pointHistory.point.id.eq(pointId),
                        pointHistory.createdDate.goe(date),
                        typeEq(type)
                        )
                .fetch();
    }

    private BooleanExpression typeEq(PointType type) {
        return type != null ? pointHistory.type.eq(type) : null;
    }
}
