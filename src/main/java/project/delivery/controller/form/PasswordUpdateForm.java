package project.delivery.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class PasswordUpdateForm {

    @Length(min = 10, max = 20)
    private String nowPassword;
    @Length(min = 10, max = 20)
    private String newPassword;
}
