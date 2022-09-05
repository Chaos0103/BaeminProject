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

    @Override
    public List<PayCardDto> findPayCardPayId(Long payId) {
        return queryFactory
                .select(fields(PayCardDto.class,
                        payCard.id,
                        payCard.card,
                        payCard.cardNumber,
                        payCard.nickname))
                .from(payCard)
                .where(payCard.pay.id.eq(payId))
                .fetch();
    }
}
