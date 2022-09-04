package project.delivery.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.delivery.domain.TimeBaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "payment_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Column(updatable = false, nullable = false)
    private Integer orderAmount;
    @Column(updatable = false)
    private String couponName;
    @Column(updatable = false)
    private Integer discountAmount;
    @Column(updatable = false)
    private Integer point;
    @Column(updatable = false)
    private Integer deliveryTip;
    @Column(updatable = false, nullable = false)
    private Integer totalAmount;

    public Payment(PaymentMethod paymentMethod, Integer orderAmount, String couponName, Integer discountAmount, Integer point, Integer deliveryTip, Integer totalAmount) {
        this.paymentMethod = paymentMethod;
        this.orderAmount = orderAmount;
        this.couponName = couponName;
        this.discountAmount = discountAmount;
        this.point = point;
        this.deliveryTip = deliveryTip;
        this.totalAmount = totalAmount;
    }
}
