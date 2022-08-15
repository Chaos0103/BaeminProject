package project.delivery.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.delivery.exception.MaxException;
import project.delivery.exception.NotEnoughBalanceException;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pay extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "pay_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false, length = 6)
    private String password;

    @Column(nullable = false)
    private int money;

    @OneToMany(mappedBy = "pay", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<PayHistory> payHistories = new ArrayList<>();

    public Pay(Member member, String password) {
        this.member = member;
        this.password = password;
        this.money = 0;
    }

    //==비즈니스 로직==//
    public void addMoney(int money) {
        int restMoney = this.money + money;
        if (restMoney > 2000000) {
            throw new MaxException("최대 200만원까지 충전가능합니다.");
        }
        this.money = restMoney;
    }

    public void removeMoney(int money) {
        int restMoney = this.money - money;
        if (restMoney < 0) {
            throw new NotEnoughBalanceException("잔액이 부족합니다.");
        }
        this.money = restMoney;
    }
}
