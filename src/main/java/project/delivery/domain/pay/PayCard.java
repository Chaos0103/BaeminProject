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
public class PayCard extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "pay_card_id")
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "pay_id")
    private Pay pay;

    @Enumerated(EnumType.STRING)
    private Card card;
    @Column(nullable = false, length = 20)
    private String nickname;
    @Column(updatable = false, nullable = false, length = 16)
    private String cardNumber;
    @Column(updatable = false, nullable = false, length = 4)
    private String expirationDate;
    @Column(updatable = false, nullable = false, length = 3)
    private String cvc;
    @Column(updatable = false, nullable = false, length = 2)
    private String password;

    public PayCard(Card card, String cardNumber, String expirationDate, String cvc, String password) {
        this.card = card;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvc = cvc;
        this.password = password;
        this.nickname = card.getDescription();
    }

    //==연관관계 메서드==//
    public void addPay(Pay pay) {
        this.pay = pay;
    }

    //==비즈니스 로직==//
    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }
}
