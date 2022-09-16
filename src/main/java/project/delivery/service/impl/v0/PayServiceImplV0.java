package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.pay.*;
import project.delivery.dto.PayAccountDto;
import project.delivery.dto.PayCardDto;
import project.delivery.dto.PayDto;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.*;
import project.delivery.service.PayService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PayServiceImplV0 implements PayService {

    private final PayRepository payRepository;
    private final PayCardRepository payCardRepository;
    private final PayAccountRepository payAccountRepository;

    @Override
    public Long chargePayMoney(Long memberId, int price, String content) {
        Pay findPay = getPay(memberId);
        PayHistory savedPayHistory = createPayHistory(findPay, price, content, TransactionType.CHARGE);
        return savedPayHistory.getId();
    }

    @Override
    public Long refundPayMoney(Long memberId, String content) {
        Pay findPay = getPay(memberId);
        int refundMoney = findPay.getMoney();
        PayHistory savedPayHistory = createPayHistory(findPay, refundMoney, content, TransactionType.REFUND);
        return savedPayHistory.getId();
    }

    @Override
    public Long usePayMoney(Long memberId, int price, String content) {
        Pay findPay = getPay(memberId);
        PayHistory savedPayHistory = createPayHistory(findPay, price, content, TransactionType.USE);
        return savedPayHistory.getId();
    }

    @Override
    public void changePayPassword(Long memberId, String password) {
        Pay findPay = getPay(memberId);
        findPay.changePassword(password);
    }

    @Override
    public void deletePay(Long memberId) {
        Pay findPay = getPay(memberId);

        payRepository.delete(findPay);
    }

    @Override
    public Long createPayCard(Long memberId, PayCard savePayCard) {
        Pay findPay = getPay(memberId);

        PayCard savedPayCard = createPayCard(savePayCard, findPay);

        return savedPayCard.getId();
    }

    @Override
    public void updatePayCardNickname(Long payCardId, String nickname) {
        PayCard findPayCard = getPayCard(payCardId);
        findPayCard.changeNickname(nickname);
    }

    @Override
    public void deletePayCard(Long payCardId) {
        PayCard findPayCard = getPayCard(payCardId);
        payCardRepository.delete(findPayCard);
    }

    @Override
    public Long createPayAccount(Long memberId, PayAccount savePayAccount) {
        Pay findPay = getPay(memberId);

        PayAccount payAccount = new PayAccount(findPay, savePayAccount.getBank(), savePayAccount.getAccountNumber());
        PayAccount savedPayAccount = payAccountRepository.save(payAccount);

        return savedPayAccount.getId();
    }

    @Override
    public void updatePayAccountNickname(Long payAccountId, String nickname) {
        PayAccount findPayAccount = getPayAccount(payAccountId);
        findPayAccount.changeNickname(nickname);
    }

    @Override
    public void deletePayAccount(Long payAccountId) {
        PayAccount findPayAccount = getPayAccount(payAccountId);
        payAccountRepository.delete(findPayAccount);
    }

    private Pay getPay(Long memberId) {
        Pay findPay = payRepository.findByMemberId(memberId).orElse(null);
        if (findPay == null) {
            throw new NoSuchException("배민페이 미가입자입니다");
        }
        return findPay;
    }

    private PayCard getPayCard(Long payCardId) {
        PayCard findPayCard = payCardRepository.findById(payCardId).orElse(null);
        if (findPayCard == null) {
            throw new NoSuchException("등록되지 않은 페이카드입니다.");
        }
        return findPayCard;
    }

    private PayAccount getPayAccount(Long payAccountId) {
        PayAccount findPayAccount = payAccountRepository.findById(payAccountId).orElse(null);
        if (findPayAccount == null) {
            throw new NoSuchException("등록되지 않은 페이계좌입니다.");
        }
        return findPayAccount;
    }

    public PayHistory createPayHistory(Pay pay, int price, String content, TransactionType type) {
        return PayHistory.createPayHistory(pay, price, content, type);
    }

    private PayCard createPayCard(PayCard savePayCard, Pay findPay) {
        PayCard payCard = new PayCard(findPay, savePayCard.getCard(), savePayCard.getCardNumber(), savePayCard.getExpirationDate(), savePayCard.getCvc(), savePayCard.getPassword());
        return payCardRepository.save(payCard);
    }
}
