package project.delivery.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePayPasswordForm {

    @NotBlank
    @Length(min = 6, max = 6)
    private String nowPassword;
    @NotBlank
    @Length(min = 6, max = 6)
    private String newPassword;
    @NotBlank
    @Length(min = 6, max = 6)
    private String checkNewPassword;
}
