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
public class Point extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "point_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int totalPoint;
    private int removePoint;
    private int balance;

    @OneToMany(mappedBy = "point", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<PointHistory> pointHistories = new ArrayList<>();

    public Point(Member member) {
        this.member = member;
        this.totalPoint = 0;
        this.removePoint = 0;
        this.balance = 0;
        member.addPoint(this);
    }

    //==연관관계 메서드==//
    public void addPointHistory(PointHistory pointHistory) {
        pointHistories.add(pointHistory);
    }

    //==비즈니스 로직==//
    public void addTotalPoint(int totalPoint) {
        int add = this.totalPoint + totalPoint;
        if (add > 500000) {
            this.balance += add - 500000;
            this.totalPoint = 500000;
        } else {
            this.totalPoint = add;
        }
    }
}
