package project.delivery.controller.form;

import lombok.Data;

@Data
public class ChangePasswordForm {

    private String password;
    private String checkPassword;
}
