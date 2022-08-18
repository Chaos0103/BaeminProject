package project.delivery.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FindEmailForm {

    @NotBlank(message = "연락처를 입력해주세요")
    private String phone;
}
