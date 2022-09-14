package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.pay.Card;
import project.delivery.domain.pay.PayCard;

@Data
public class PayCardDto {

    private Long id;
    private Card card;
    private String cardNumber;
    private String nickname;

    public PayCardDto(PayCard payCard) {
        this.id = payCard.getId();
        this.card = payCard.getCard();
        this.cardNumber = payCard.getCardNumber();
        this.nickname = payCard.getNickname();
    }
}
