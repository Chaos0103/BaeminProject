package project.delivery.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class PasswordFindForm {

    @Email
    @Length(max = 50)
    @NotBlank
    private String email;

    @NotBlank
    @Length(max = 11)
    private String phone;
}
