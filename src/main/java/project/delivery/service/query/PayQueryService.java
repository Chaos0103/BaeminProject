package project.delivery.service.query;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.dto.PayAccountDto;
import project.delivery.dto.PayCardDto;
import project.delivery.dto.PayDto;

import java.util.List;

@Transactional(readOnly = true)
public interface PayQueryService {

    /**
     * 배민페이 비밀번호를 조회하는 로직
     * @param memberId 조회할 회원의 id
     * @return 배민페이 비밀번호
     * @exception project.delivery.exception.NoSuchException 배민페이 미가입자인 경우
     */
    String findPayPassword(Long memberId);

    /**
     * 배민페이 정보와 내역을 조회하는 로직
     * @param memberId 조회할 회원의 id
     * @return PayDto
     */
    PayDto findPay(Long memberId);

    /**
     * 배민페이 카드 정보를 조회하는 로직
     * @param payId 조회할 페이의 id
     * @return PayCardDto
     */
    List<PayCardDto> findPayCard(Long payId);

    /**
     * 배민페이 계좌 정보를 조회하는 로직
     * @param payId 조회할 페이의 id
     * @return PayAccountDto
     */
    List<PayAccountDto> findPayAccount(Long payId);

    /**
     * 배민페이 잔액을 조회하는 로직
     * @param memberId 조회할 회원의 id
     * @return 배민페이 잔액
     */
    Integer findMoney(Long memberId);
}
