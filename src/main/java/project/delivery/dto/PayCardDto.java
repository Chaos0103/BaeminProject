package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.pay.Card;

@Data
public class PayCardDto {

    private Long id;
    private Card card;
    private String cardNumber;
    private String nickname;
}
