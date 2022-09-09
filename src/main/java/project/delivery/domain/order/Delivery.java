package project.delivery.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.delivery.domain.Address;
import project.delivery.domain.TimeBaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @Embedded
    private Address address;
    @Column(nullable = false, length = 13)
    private String phone;
    @Column(nullable = false, length = 13)
    private String safeNumber;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
    @Column(nullable = false)
    private String requirement;

    public Delivery(Address address, String phone, String safeNumber, String requirement) {
        this.address = address;
        this.phone = phone;
        this.safeNumber = safeNumber;
        this.requirement = requirement;

        this.status = DeliveryStatus.DELIVERY;
    }
}
