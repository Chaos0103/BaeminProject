package project.delivery.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import project.delivery.domain.pay.Card;

@Data
public class PayCardSaveForm {

    private Card card;
    @Length(min = 16, max = 16)
    private String cardNumber;
    @Length(min = 4, max = 4)
    private String expirationDate;
    @Length(min = 3, max = 3)
    private String cvc;
    @Length(min = 2, max = 2)
    private String password;
}
