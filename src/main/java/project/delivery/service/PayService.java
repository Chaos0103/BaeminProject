package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface PayService {

    /**
     * 페이머니충전:
     * @return 페이내역고유번호
     */
    @Transactional
    Long chargePayMoney(Long memberId, int price);
}
