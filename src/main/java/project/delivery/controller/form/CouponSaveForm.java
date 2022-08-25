package project.delivery.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CouponSaveForm {

    @Length(min = 4, max = 4)
    private String first;
    @Length(min = 4, max = 4)
    private String second;
    @Length(min = 4, max = 4)
    private String third;
    @Length(min = 4, max = 4)
    private String fourth;
}
