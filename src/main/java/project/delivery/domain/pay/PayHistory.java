package project.delivery.domain.pay;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.delivery.domain.TimeBaseEntity;

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
    @Column(updatable = false, nullable = false)
    private String content;
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    public PayHistory(Pay pay, int price, String content, TransactionType type) {
        this.pay = pay;
        this.price = price;
        this.content = content;
        this.type = type;
    }

    //==생성 메서드==//
    public static PayHistory createPayHistory(Pay pay, int price, String content, TransactionType type) {
        PayHistory payHistory = new PayHistory(pay, price, content, type);
        if (type.equals(TransactionType.CHARGE)) {
            pay.addMoney(price);
        } else {
            pay.removeMoney(price);
        }
        pay.getPayHistories().add(payHistory);

        return payHistory;
    }
}
