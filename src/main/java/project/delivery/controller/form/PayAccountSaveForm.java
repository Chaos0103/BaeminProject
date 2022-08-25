package project.delivery.controller.form;

import lombok.Data;
import project.delivery.domain.Bank;

import javax.validation.constraints.NotBlank;

@Data
public class PayAccountSaveForm {

    private Bank bank;

    @NotBlank
    private String accountNumber;
}
