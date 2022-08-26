package project.delivery.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class VoucherSaveForm {

    @Length(min = 12, max = 12)
    private String voucherCode;
}
