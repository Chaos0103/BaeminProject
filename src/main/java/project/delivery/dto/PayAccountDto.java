package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.Bank;
import project.delivery.domain.PayAccount;

@Data
public class PayAccountDto {

    private Long id;
    private Bank bank;
    private String accountNumber;

    public PayAccountDto(PayAccount payAccount) {
        this.id = payAccount.getId();
        this.bank = payAccount.getBank();
        this.accountNumber = payAccount.getAccountNumber();
    }
}
