package project.delivery.controller.form;

import lombok.Data;

@Data
public class OrderAddForm {

    private Boolean disposable;
    private Boolean sideDish;
    private String requirement;
}
