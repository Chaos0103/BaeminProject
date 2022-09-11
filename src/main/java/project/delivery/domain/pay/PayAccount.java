package project.delivery.domain.pay;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.delivery.domain.TimeBaseEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayAccount extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "pay_account_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pay_id")
    private Pay pay;

    @Enumerated(EnumType.STRING)
    private Bank bank;
    @Column(updatable = false, nullable = false, length = 20)
    private String accountNumber;
    @Column(nullable = false, length = 20)
    private String nickname;

    public PayAccount(Pay pay, Bank bank, String accountNumber) {
        this.pay = pay;
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.nickname = bank.getDescription();
    }

    //==연관관계 메서드==//
    public void addPay(Pay pay) {
        this.pay = pay;
    }

    //==비즈니스 메서드==//
    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }
}
