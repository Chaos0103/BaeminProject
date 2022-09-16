package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.pay.PayAccount;
import project.delivery.domain.pay.PayCard;

@Transactional
public interface PayService {

    /**
     * 배민페이머니 충전하는 로직
     * @param memberId 회원 id
     * @param price 충전할 금액
     * @param content 내용
     * @return 배민페이머니 사용내역 id
     * @exception project.delivery.exception.NoSuchException 배민페이 미가입자인 경우 예외 발생
     */
    Long chargePayMoney(Long memberId, int price, String content);

    /**
     * 배민페이머니 전액 환불하는 로직
     * @param memberId 회원 id
     * @param content 내용
     * @return 배민페이머니 사용내역 id
     * @exception project.delivery.exception.NoSuchException 배민페이 미가입자인 경우 예외 발생
     */
    Long refundPayMoney(Long memberId, String content);

    /**
     * 배민페이머니 사용하는 로직
     * @param memberId 회원 id
     * @param price 사용할 금액
     * @param content 내용
     * @return 배민페이머니 사용내역 id
     * @exception project.delivery.exception.NoSuchException 배민페이 미가입자인 경우 예외 발생
     */
    Long usePayMoney(Long memberId, int price, String content);

    /**
     * 배민페이 비밀번호를 변경하는 로직
     * @param memberId 회원 id
     * @param password 변경할 비밀번호
     * @exception project.delivery.exception.NoSuchException 배민페이 미가입자인 경우 예외 발생
     */
    void changePayPassword(Long memberId, String password);

    /**
     * 페이탈퇴
     */
    void deletePay(Long memberId);

    /**
     * 배민페이 카드를 등록하는 로직
     * @param memberId 회원 id
     * @param savePayCard 등록할 페이카드 데이터
     * @return 등록된 카드 id
     * @exception project.delivery.exception.NoSuchException 배민페이 미가입자인 경우 예외 발생
     */
    Long createPayCard(Long memberId, PayCard savePayCard);

    /**
     * 배민페이 카드의 별명을 변경하는 로직
     * @param payCardId 닉네임을 변경할 페이카드 id
     * @param nickname 변경할 닉네임
     * @exception project.delivery.exception.NoSuchException 등록되지 않은 배민페이 카드인 경우
     */
    void updatePayCardNickname(Long payCardId, String nickname);

    /**
     * 배민페이 카드를 제거하는 로직
     * @param payCardId 제거할 페이카드 id
     * @exception project.delivery.exception.NoSuchException 등록되지 않은 배민페이 카드인 경우
     */
    void deletePayCard(Long payCardId);

    /**
     * 배민페이 계좌를 동륵하는 로직
     * @param memberId 계좌를 등록할 회원의 id
     * @param savePayAccount 등록할 페이계좌 데이터
     * @return 등록된 계좌 id
     * @exception project.delivery.exception.NoSuchException 배민페이 미가입자인 경우
     */
    Long createPayAccount(Long memberId, PayAccount savePayAccount);

    /**
     * 배민페이 계좌의 별명을 변경하는 로직
     * @param payAccountId 별명을 변경할 페이계좌의 id
     * @param nickname 변경할 닉네임
     * @exception project.delivery.exception.NoSuchException 등록되지 않은 배민페이 계좌인 경우
     */
    void updatePayAccountNickname(Long payAccountId, String nickname);

    /**
     * 배민페이 계좌를 제거하는 로직
     * @param payAccountId 제거할 페이계좌의 id
     * @exception project.delivery.exception.NoSuchException 등록되지 않은 배민페이 계좌인 경우
     */
    void deletePayAccount(Long payAccountId);
}
