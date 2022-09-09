package project.delivery.controller.form;

import lombok.Data;

@Data
public class ReviewAddForm {

    private Integer rating;
    private String content;
    private String uploadFileName;
}
