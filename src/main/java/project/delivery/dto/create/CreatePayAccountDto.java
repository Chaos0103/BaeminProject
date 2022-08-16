package project.delivery.dto.create;

import lombok.Data;
import project.delivery.domain.Bank;

@Data
public class CreatePayAccountDto {

    private Bank bank;
    private String accountNumber;

    public CreatePayAccountDto(Bank bank, String accountNumber) {
        this.bank = bank;
        this.accountNumber = accountNumber;
    }
}
