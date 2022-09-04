package project.delivery.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import project.delivery.dto.NotificationDto;
import project.delivery.repository.custom.NotificationRepositoryCustom;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static project.delivery.domain.QNotification.*;

public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public NotificationRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<NotificationDto> findNotificationByMemberId(Long memberId, LocalDateTime period) {
        return queryFactory
                .select(Projections.fields(NotificationDto.class,
                        notification.storeName,
                        notification.type,
                        notification.createdDate))
                .from(notification)
                .where(
                        notification.member.id.eq(memberId),
                        notification.createdDate.goe(period)
                )
                .fetch();
    }
}
