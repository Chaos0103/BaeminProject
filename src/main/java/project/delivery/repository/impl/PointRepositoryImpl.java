package project.delivery.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import project.delivery.domain.point.PointType;
import project.delivery.dto.PointHistoryDto;
import project.delivery.repository.custom.PointRepositoryCustom;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.querydsl.core.types.Projections.*;
import static project.delivery.domain.QPointHistory.*;

public class PointRepositoryImpl implements PointRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PointRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PointHistoryDto> findPointHistoryByPointId(Long pointId, PointType type, LocalDateTime period) {
        return queryFactory
                .select(fields(PointHistoryDto.class,
                        pointHistory.content,
                        pointHistory.pointValue,
                        pointHistory.type,
                        pointHistory.createdDate))
                .from(pointHistory)
                .where(
                        pointHistory.point.id.eq(pointId),
                        pointHistory.createdDate.goe(period),
                        typeEq(type)
                )
                .fetch();
    }

    private BooleanExpression typeEq(PointType type) {
        return type != null ? pointHistory.type.eq(type) : null;
    }
}
