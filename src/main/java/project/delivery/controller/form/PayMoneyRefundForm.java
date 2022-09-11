package project.delivery.controller.form;

import lombok.Data;
import project.delivery.domain.pay.Bank;

@Data
public class PayMoneyRefundForm {

    private Bank bank;
}
