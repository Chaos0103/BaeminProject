package project.delivery.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class NicknameUpdateForm {

    @Length(min = 2, max = 10)
    private String newNickname;
}
