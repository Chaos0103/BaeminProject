package project.delivery.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
