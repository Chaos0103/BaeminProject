package project.delivery.controller.form;

import lombok.Data;
import project.delivery.domain.order.PaymentMethod;

@Data
public class PaymentAddForm {

    private PaymentMethod paymentMethod;
    private Integer orderAmount;
    private Long couponId;
    private Integer point;
    private Integer deliveryTip;
    private Integer totalAmount;
}
