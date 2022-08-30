package project.delivery.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryTipByArea {

    @Id @GeneratedValue
    @Column(name = "delivery_tip_by_area_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_info_id")
    private DeliveryInfo deliveryInfo;

    private String areaInfo;
    private Integer deliveryTip;

    public DeliveryTipByArea(DeliveryInfo deliveryInfo, String areaInfo, Integer deliveryTip) {
        this.deliveryInfo = deliveryInfo;
        this.areaInfo = areaInfo;
        this.deliveryTip = deliveryTip;
    }
}
