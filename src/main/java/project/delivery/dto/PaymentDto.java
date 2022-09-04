package project.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.delivery.domain.order.PaymentMethod;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private PaymentMethod paymentMethod;
    private Integer orderAmount;
    private Long couponId;
    private Integer point;
    private Integer deliveryTip;
    private Integer totalAmount;
}
