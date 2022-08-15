package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.Pay;
import project.delivery.domain.PayHistory;
import project.delivery.domain.TransactionType;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.PayRepository;
import project.delivery.service.PayService;

@Service
@RequiredArgsConstructor
public class PayServiceImplV0 implements PayService {

    private final PayRepository payRepository;

    @Override
    public Long chargePayMoney(Long memberId, int price) {
        Pay findPay = getPay(memberId);

        PayHistory payHistory = PayHistory.createPayHistory(findPay, price, TransactionType.CHARGE);

        return payHistory.getId();
    }

    private Pay getPay(Long memberId) {
        return payRepository.findByMemberId(memberId).orElseThrow(() -> {
            throw new NoSuchException("배민페이 미가입자입니다.");
        });
    }
}
