package project.delivery.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EmailFindForm {

    @NotBlank
    private String phone;
    private String authenticationNumber;
    
}
