package project.delivery.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayCard extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "pay_card_id")
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "pay_id")
    private Pay pay;

    @Column(updatable = false, nullable = false, length = 16)
    private String cardNumber;
    @Column(updatable = false, nullable = false, length = 4)
    private String expirationDate;
    @Column(updatable = false, nullable = false, length = 3)
    private String cvc;
    @Column(updatable = false, nullable = false, length = 2)
    private String password;

    public PayCard(Pay pay, String cardNumber, String expirationDate, String cvc, String password) {
        this.pay = pay;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvc = cvc;
        this.password = password;
    }
}
