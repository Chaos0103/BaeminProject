package project.delivery.domain.store;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.delivery.domain.TimeBaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryTipByAmount extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "delivery_tip_by_amount_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_info_id")
    private DeliveryInfo deliveryInfo;

    private Integer minAmount;
    private Integer maxAmount;
    private Integer deliveryTip;

    public DeliveryTipByAmount(DeliveryInfo deliveryInfo, Integer minAmount, Integer maxAmount, Integer deliveryTip) {
        this.deliveryInfo = deliveryInfo;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.deliveryTip = deliveryTip;
    }
}
