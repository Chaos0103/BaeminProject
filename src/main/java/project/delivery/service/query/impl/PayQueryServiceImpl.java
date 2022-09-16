package project.delivery.service.query.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.pay.Pay;
import project.delivery.domain.pay.PayAccount;
import project.delivery.domain.pay.PayCard;
import project.delivery.dto.PayAccountDto;
import project.delivery.dto.PayCardDto;
import project.delivery.dto.PayDto;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.PayAccountRepository;
import project.delivery.repository.PayCardRepository;
import project.delivery.repository.PayRepository;
import project.delivery.service.query.PayQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PayQueryServiceImpl implements PayQueryService {

    private final PayRepository payRepository;
    private final PayCardRepository payCardRepository;
    private final PayAccountRepository payAccountRepository;

    @Override
    public String findPayPassword(Long memberId) {
        String password = payRepository.findPayPasswordByMemberId(memberId).orElse(null);
        if (password == null) {
            throw new NoSuchException("배민페이 미가입자입니다");
        }
        return password;
    }

    @Override
    public PayDto findPay(Long memberId) {
        Pay pay = payRepository.findPayByMemberId(memberId).orElse(null);
        if (pay == null) {
            throw new NoSuchException("배민페이 미가입자입니다");
        }
        return new PayDto(pay);
    }

    @Override
    public List<PayCardDto> findPayCard(Long payId) {
        List<PayCard> payCards = payCardRepository.findPayCardPayId(payId);
        return payCards.stream()
                .map(PayCardDto::new)
                .toList();
    }

    @Override
    public List<PayAccountDto> findPayAccount(Long payId) {
        List<PayAccount> payAccounts = payAccountRepository.findPayAccountByPayId(payId);
        return payAccounts.stream()
                .map(PayAccountDto::new)
                .toList();
    }

    @Override
    public Integer findMoney(Long memberId) {
        return payRepository.findMoney(memberId);
    }
}
