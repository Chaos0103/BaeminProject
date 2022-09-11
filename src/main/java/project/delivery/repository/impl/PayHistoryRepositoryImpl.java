package project.delivery.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.delivery.domain.pay.PayHistory;
import project.delivery.domain.pay.TransactionType;
import project.delivery.repository.custom.PayHistoryRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static project.delivery.domain.QPayHistory.*;

public class PayHistoryRepositoryImpl implements PayHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PayHistoryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PayHistory> findAllByTransactionType(Long payId, TransactionType type) {
        return queryFactory
                .selectFrom(payHistory)
                .where(
                        payHistory.pay.id.eq(payId),
                        payHistory.type.eq(type)
                )
                .fetch();
    }
}
