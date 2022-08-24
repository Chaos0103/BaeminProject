package project.delivery.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class PasswordChangeForm {

    @Length(min = 10, max = 16)
    private String password;
    @Length(min = 10, max = 16, message = "비밀번호가 일치하지 않습니다")
    private String checkPassword;
}
