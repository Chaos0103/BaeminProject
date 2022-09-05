package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.*;
import project.delivery.dto.PayAccountDto;
import project.delivery.dto.PayCardDto;
import project.delivery.dto.PayDto;

import java.util.List;

@Transactional(readOnly = true)
public interface PayService {

    /**
     * 페이등록: 각 회원은 단 하나의 페이에 가입이 가능
     * @return 페이고유번호
     */
    @Transactional
    Long joinPay(Long memberId, String password);

    @Transactional
    void chargePayMoney(Long memberId, int money);

    @Transactional
    Integer refundPayMoney(Long memberId, String content);

    /**
     * 페이내역등록
     * @return 페이내역고유번호
     */
    @Transactional
    Long createPayHistory(Long memberId, int price, String content, TransactionType type);

    /**
     * 페이비밀번호변경
     */
    @Transactional
    void changePayPassword(Long memberId, String password);

    /**
     * 페이탈퇴
     */
    @Transactional
    void deletePay(Long memberId);

    /**
     * 페이카드등록
     * @return 페이카드고유번호
     */
    @Transactional
    Long createPayCard(Long memberId, PayCard payCard);

    @Transactional
    void updatePayCardNickname(Long payCardId, String nickname);

    /**
     * 페이카드삭제
     */
    @Transactional
    void deletePayCard(Long payCardId);

    /**
     * 페이계좌등록
     * @return 페이계좌고유번호
     */
    @Transactional
    Long createPayAccount(Long memberId, PayAccount payAccount);

    @Transactional
    void updatePayAccountNickname(Long payAccountId, String nickname);

    /**
     * 페이계좌삭제
     */
    @Transactional
    void deletePayAccount(Long payAccountId);

    PayDto findPayByMemberId(Long memberId);

    /**
     * 페이내역조회
     *
     * @param type TransactionType
     * @return PayHistoryDto
     */
    List<PayHistory> findPayHistory(Long memberId, TransactionType type);

    /**
     * 페이카드조회
     *
     * @return PayCardDto
     */
    List<PayCardDto> findPayCardByPayId(Long payId);

    /**
     * 페이계좌조회
     *
     * @return PayAccountDto
     */
    List<PayAccountDto> findPayAccountByPayId(Long payId);

    Integer findMoney(Long memberId);
}
