package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.TransactionType;

@Transactional(readOnly = true)
public interface PayService {

    /**
     * 페이내역등록
     * @return 페이내역고유번호
     */
    @Transactional
    Long createPayHistory(Long memberId, int price, String content, TransactionType type);
}
