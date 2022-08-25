package project.delivery.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class PasswordChangeForm {

    @Length(min = 10, max = 16)
    private String password;
    @Length(min = 10, max = 16)
    private String checkPassword;
}
