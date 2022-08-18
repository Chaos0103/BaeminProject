package project.delivery.repository.custom;

import project.delivery.domain.PayHistory;
import project.delivery.domain.TransactionType;

import java.util.List;

public interface PayHistoryRepositoryCustom {

    List<PayHistory> findAllByTransactionType(Long payId, TransactionType type);
}
