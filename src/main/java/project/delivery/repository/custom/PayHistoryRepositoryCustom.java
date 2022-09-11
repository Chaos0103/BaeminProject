package project.delivery.repository.custom;

import project.delivery.domain.pay.PayHistory;
import project.delivery.domain.pay.TransactionType;

import java.util.List;

public interface PayHistoryRepositoryCustom {

    List<PayHistory> findAllByTransactionType(Long payId, TransactionType type);
}
