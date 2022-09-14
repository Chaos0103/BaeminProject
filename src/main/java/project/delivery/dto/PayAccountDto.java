package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.pay.Bank;
import project.delivery.domain.pay.PayAccount;

@Data
public class PayAccountDto {

    private Long id;
    private String accountNumber;
    private Bank bank;
    private String nickname;

    public PayAccountDto(PayAccount payAccount) {
        this.id = payAccount.getId();
        this.accountNumber = payAccount.getAccountNumber();
        this.bank = payAccount.getBank();
        this.nickname = payAccount.getNickname();
    }
}
