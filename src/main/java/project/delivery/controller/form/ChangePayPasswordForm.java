package project.delivery.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ChangePayPasswordForm {

    @Length(min = 6, max = 6)
    private String nowPassword;
    @Length(min = 6, max = 6)
    private String newPassword;
    @Length(min = 6, max = 6)
    private String checkNewPassword;
}
