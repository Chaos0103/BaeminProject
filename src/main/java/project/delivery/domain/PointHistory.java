package project.delivery.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointHistory extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "point_history_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "point_id")
    private Point point;

    private int pointValue;
    private String content;
    private PointType type;

    public PointHistory(int pointValue, String content, PointType type) {
        this.pointValue = pointValue;
        this.content = content;
        this.type = type;
    }

    //==연관관계 메서드==//
    public void addPoint(Point point) {
        this.point = point;
        point.addPointHistory(this);
    }
}
