package project.delivery.controller.form;

import lombok.Data;

@Data
public class DeliveryAddForm {

    private String zipcode;
    private String mainAddress;
    private String detailAddress;
    private String phone;
    private Boolean safeNumber;
    private String requirement;
}
