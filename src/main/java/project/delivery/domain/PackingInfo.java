package project.delivery.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String position;
    @Column(nullable = false)
    private PaymentType paymentType;

    public PackingInfo(Store store, int minOrderPrice, String pickUpTime, String position, PaymentType paymentType) {
        this.store = store;
        this.minOrderPrice = minOrderPrice;
        this.pickUpTime = pickUpTime;
        this.position = position;
        this.paymentType = paymentType;
        store.addPackingInfo(this);
    }
}
