package project.delivery.repository.custom;

import project.delivery.domain.Card;
import project.delivery.dto.PayCardDto;

import java.util.List;

public interface PayCardRepositoryCustom {

    /**
     *
     * @param payId 페이PK
     * @return id, card, cardNumber, nickname
     */
    List<PayCardDto> findPayCardPayId(Long payId);
}
