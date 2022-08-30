package project.delivery.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryInfo extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "delivery_info_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private int minOrderPrice;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType type;
    @Column(nullable = false)
    private String deliveryTime;

    public DeliveryInfo(Store store, int minOrderPrice, PaymentType type, String deliveryTime) {
        this.store = store;
        this.minOrderPrice = minOrderPrice;
        this.type = type;
        this.deliveryTime = deliveryTime;

        store.addDeliveryInfo(this);
    }
}
