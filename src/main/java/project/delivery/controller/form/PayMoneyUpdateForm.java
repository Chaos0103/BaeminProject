package project.delivery.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import project.delivery.domain.pay.Bank;

@Data
public class PayMoneyUpdateForm {

    @Range(min = 10000, max = 2000000)
    private Integer money;

    private Bank bank;
}
