package project.delivery.controller.form;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BasketAddForm {

    private Long menuOptionId;
    private List<Long> menuSubOptionIds = new ArrayList<>();
    private Integer count;
}
