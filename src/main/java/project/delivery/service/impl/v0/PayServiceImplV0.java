package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.*;
import project.delivery.dto.PayAccountDto;
import project.delivery.dto.PayCardDto;
import project.delivery.dto.PayHistoryDto;
import project.delivery.dto.create.CreatePayAccountDto;
import project.delivery.dto.create.CreatePayCardDto;
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
    public Long createPayCard(Long memberId, CreatePayCardDto createPayCardDto) {
        Pay findPay = getPay(memberId);

        PayCard payCard = createPayCard(createPayCardDto, findPay);
        PayCard savePayCard = payCardRepository.save(payCard);

        return savePayCard.getId();
    }

    @Override
    public void deletePayCard(Long payCardId) {
        PayCard findPayCard = getPayCard(payCardId);
        payCardRepository.delete(findPayCard);
    }

    @Override
    public Long createPayAccount(Long memberId, CreatePayAccountDto createPayAccountDto) {
        Pay findPay = getPay(memberId);

        PayAccount payAccount = createPayAccount(createPayAccountDto, findPay);
        PayAccount savedPayAccount = payAccountRepository.save(payAccount);

        return savedPayAccount.getId();
    }

    @Override
    public void deletePayAccount(Long payAccountId) {
        PayAccount findPayAccount = getPayAccount(payAccountId);
        payAccountRepository.delete(findPayAccount);
    }

    @Override
    public List<PayHistoryDto> findPayHistory(Long memberId, TransactionType type) {
        Pay findPay = getPay(memberId);

        List<PayHistory> findPayHistories = payHistoryRepository.findAllByTransactionType(findPay.getId(), type);

        return findPayHistories.stream()
                .map(PayHistoryDto::new)
                .toList();
    }

    @Override
    public List<PayCardDto> findPayCard(Long memberId) {
        Pay findPay = getPay(memberId);

        List<PayCard> findPayCards = payCardRepository.findByPayId(findPay.getId());

        return findPayCards.stream()
                .map(PayCardDto::new)
                .toList();
    }

    @Override
    public List<PayAccountDto> findPayAccount(Long memberId) {
        Pay findPay = getPay(memberId);

        List<PayAccount> findPayAccounts = payAccountRepository.findByPayId(findPay.getId());

        return findPayAccounts.stream()
                .map(PayAccountDto::new)
                .toList();
    }

    private Pay createPay(String password, Member findMember) {
        return new Pay(findMember, password);
    }

    private PayCard createPayCard(CreatePayCardDto createPayCardDto, Pay findPay) {
        return new PayCard(findPay, createPayCardDto.getCardNumber(), createPayCardDto.getExpirationDate(), createPayCardDto.getCvc(), createPayCardDto.getPassword());
    }

    private PayAccount createPayAccount(CreatePayAccountDto createPayAccountDto, Pay findPay) {
        return new PayAccount(findPay, createPayAccountDto.getBank(), createPayAccountDto.getAccountNumber());
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> {
            throw new NoSuchException("등록되지 않은 회원입니다.");
        });
    }

    private Pay getPay(Long memberId) {
        return payRepository.findByMemberId(memberId).orElseThrow(() -> {
            throw new NoSuchException("배민페이 미가입자입니다.");
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
