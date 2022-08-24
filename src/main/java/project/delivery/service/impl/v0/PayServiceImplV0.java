package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.*;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.*;
import project.delivery.service.PayService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PayServiceImplV0 implements PayService {

    private final PayRepository payRepository;
    private final PayHistoryRepository payHistoryRepository;
    private final PayCardRepository payCardRepository;
    private final PayAccountRepository payAccountRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long joinPay(Long memberId, String password) {
        Member findMember = getMember(memberId);

        Pay pay = createPay(password, findMember);
        Pay savedPay = payRepository.save(pay);

        return savedPay.getId();
    }

    @Override
    public void chargePayMoney(Long memberId, int money) {
        Pay findPay = getPay(memberId);
        findPay.addMoney(money);
    }

    @Override
    public void refundPayMoney(Long memberId) {
        Pay findPay = getPay(memberId);
        createPayHistory(memberId, findPay.getMoney(), "배민페이", TransactionType.REFUND);
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
    public Long createPayHistory(Long memberId, int price, String content, TransactionType type) {
        Pay findPay = getPay(memberId);

        PayHistory payHistory = PayHistory.createPayHistory(findPay, price, content, type);

        return payHistory.getId();
    }

    @Override
    public Long createPayCard(Long memberId, PayCard payCard) {
        Pay findPay = getPay(memberId);

        //연관관계 주입
        payCard.addPay(findPay);
        PayCard savePayCard = payCardRepository.save(payCard);

        return savePayCard.getId();
    }

    @Override
    public void deletePayCard(Long payCardId) {
        PayCard findPayCard = getPayCard(payCardId);
        payCardRepository.delete(findPayCard);
    }

    @Override
    public Long createPayAccount(Long memberId, PayAccount payAccount) {
        Pay findPay = getPay(memberId);

        //연관관계 주입
        payAccount.addPay(findPay);
        PayAccount savedPayAccount = payAccountRepository.save(payAccount);

        return savedPayAccount.getId();
    }

    @Override
    public void deletePayAccount(Long payAccountId) {
        PayAccount findPayAccount = getPayAccount(payAccountId);
        payAccountRepository.delete(findPayAccount);
    }

    @Override
    public Pay findPay(Long memberId) {
        return payRepository.findDataByMemberId(memberId).orElseThrow(() -> {
            throw new NoSuchException("배민페이 미가입자입니다");
        });

    }

    @Override
    public List<PayHistory> findPayHistory(Long memberId, TransactionType type) {
        Pay findPay = getPay(memberId);

        return payHistoryRepository.findAllByTransactionType(findPay.getId(), type);
    }

    @Override
    public List<PayCard> findPayCard(Long memberId) {
        Pay findPay = getPay(memberId);

        return payCardRepository.findByPayId(findPay.getId());
    }

    @Override
    public List<PayAccount> findPayAccount(Long memberId) {
        Pay findPay = getPay(memberId);

        return payAccountRepository.findByPayId(findPay.getId());
    }

    private Pay createPay(String password, Member findMember) {
        return new Pay(findMember, password);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> {
            throw new NoSuchException("등록되지 않은 회원입니다.");
        });
    }

    private Pay getPay(Long memberId) {
        return payRepository.findByMemberId(memberId).orElseThrow(() -> {
            throw new NoSuchException("배민페이 미가입자입니다");
        });
    }

    private PayCard getPayCard(Long payCardId) {
        return payCardRepository.findById(payCardId).orElseThrow(() -> {
            throw new NoSuchException("등록되지 않은 페이카드입니다.");
        });
    }

    private PayAccount getPayAccount(Long payAccountId) {
        return payAccountRepository.findById(payAccountId).orElseThrow(() -> {
            throw new NoSuchException("등록되지 않은 페이계좌입니다.");
        });
    }
}
