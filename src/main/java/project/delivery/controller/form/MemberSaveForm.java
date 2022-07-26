package project.delivery.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class MemberSaveForm {

    @Email
    @Length(max = 50)
    @NotBlank
    private String email;
    @Length(max = 16)
    @NotBlank
    private String password;
    @Length(max = 16)
    @NotBlank
    private String checkPassword;
    @Length(max = 20)
    @NotBlank
    private String username;
    @Length(max = 13)
    @NotBlank
    private String phone;
    @Length(max = 8)
    @NotBlank
    private String birth;
    @Length(max = 10)
    @NotBlank
    private String nickname;
    @Length(max = 5)
    @NotBlank
    private String zipcode;
    @Length(max = 255)
    @NotBlank
    private String mainAddress;
    @Length(max = 255)
    @NotBlank
    private String detailAddress;
}
