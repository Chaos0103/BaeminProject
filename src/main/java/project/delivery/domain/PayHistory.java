package project.delivery.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayHistory extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "pay_history_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pay_id")
    private Pay pay;

    @Column(updatable = false, nullable = false)
    private int price;
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    public PayHistory(Pay pay, int price, TransactionType type) {
        this.pay = pay;
        this.price = price;
        this.type = type;
        addPayHistory();
    }

    //==연관관계 메서드==//
    public void addPayHistory() {
        this.pay.getPayHistories().add(this);
    }
}
