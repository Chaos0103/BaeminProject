package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.pay.Bank;

@Data
public class PayAccountDto {

    private Long id;
    private String accountNumber;
    private Bank bank;
    private String nickname;
}
