package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.DeliveryInfo;
import project.delivery.domain.PaymentType;

@Data
public class DeliveryInfoDto {

    private Long id;
    private int minOrderPrice;
    private PaymentType type;
    private String deliveryTime;
    private String deliveryTip;

    public DeliveryInfoDto(DeliveryInfo deliveryInfo) {
        this.id = deliveryInfo.getId();
        this.minOrderPrice = deliveryInfo.getMinOrderPrice();
        this.type = deliveryInfo.getType();
        this.deliveryTime = deliveryInfo.getDeliveryTime();
        this.deliveryTip = deliveryInfo.getDeliveryTip();
    }
}
