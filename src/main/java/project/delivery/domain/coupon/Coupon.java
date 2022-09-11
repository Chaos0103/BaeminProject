package project.delivery.domain.coupon;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.delivery.domain.TimeBaseEntity;
import project.delivery.domain.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "coupon_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(updatable = false, nullable = false, length = 16)
    private String couponCode;
    @Column(updatable = false, nullable = false)
    private String couponName;
    @Column(updatable = false, nullable = false)
    private int minOrderPrice;
    @Column(updatable = false, nullable = false)
    private int discountPrice;
    @Enumerated(EnumType.STRING)
    private CouponStatus status;
    @Column(updatable = false, nullable = false)
    private LocalDateTime expirationDate;

    public Coupon(Member member, String couponCode, String couponName, int minOrderPrice, int discountPrice, LocalDateTime expirationDate) {
        this.member = member;
        this.couponCode = couponCode;
        this.couponName = couponName;
        this.minOrderPrice = minOrderPrice;
        this.discountPrice = discountPrice;
        this.expirationDate = expirationDate;

        this.status = CouponStatus.UNUSE;
    }

    //==비즈니스 로직==//
    public void changeStatus(CouponStatus status) {
        this.status = status;
    }
}
