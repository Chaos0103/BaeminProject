package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.PayCard;

@Data
public class PayCardDto {

    private Long id;
    private String cardNumber;
    private String expirationDate;
    private String cvc;
    private String password;

    public PayCardDto(PayCard payCard) {
        this.id = payCard.getId();
        this.cardNumber = payCard.getCardNumber();
        this.expirationDate = payCard.getExpirationDate();
        this.cvc = payCard.getCvc();
        this.password = payCard.getPassword();
    }
}
