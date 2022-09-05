package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.Bank;

@Data
public class PayAccountDto {

    private Long id;
    private String accountNumber;
    private Bank bank;
    private String nickname;
}
