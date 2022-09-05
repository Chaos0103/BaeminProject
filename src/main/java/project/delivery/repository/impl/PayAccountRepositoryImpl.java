package project.delivery.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.delivery.dto.PayAccountDto;
import project.delivery.repository.custom.PayAccountRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static com.querydsl.core.types.Projections.*;
import static project.delivery.domain.QPayAccount.*;

public class PayAccountRepositoryImpl implements PayAccountRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PayAccountRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PayAccountDto> findPayAccountByPayId(Long payId) {
        return queryFactory
                .select(fields(PayAccountDto.class,
                        payAccount.id,
                        payAccount.accountNumber,
                        payAccount.bank,
                        payAccount.nickname))
                .from(payAccount)
                .where(payAccount.pay.id.eq(payId))
                .fetch();
    }
}
