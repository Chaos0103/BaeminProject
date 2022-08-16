package project.delivery.dto.create;

import lombok.Data;

@Data
public class CreatePayCardDto {

    private String cardNumber;
    private String expirationDate;
    private String cvc;
    private String password;

    public CreatePayCardDto(String cardNumber, String expirationDate, String cvc, String password) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvc = cvc;
        this.password = password;
    }
}
