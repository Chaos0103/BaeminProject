package project.delivery.domain.store;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.delivery.domain.TimeBaseEntity;
import project.delivery.domain.pay.PaymentType;
import project.delivery.domain.store.Store;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PackingInfo extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "packing_info_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(nullable = false)
    private int minOrderPrice;
    @Column(nullable = false)
    private String pickUpTime;
    @Column(nullable = false)
    private PaymentType paymentType;

    public PackingInfo(Store store, int minOrderPrice, String pickUpTime, PaymentType paymentType) {
        this.store = store;
        this.minOrderPrice = minOrderPrice;
        this.pickUpTime = pickUpTime;
        this.paymentType = paymentType;
        store.addPackingInfo(this);
    }
}
