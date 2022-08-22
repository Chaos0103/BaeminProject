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

    public PointHistory(Point point, int pointValue, String content, PointType type) {
        this.point = point;
        this.pointValue = pointValue;
        this.content = content;
        this.type = type;
        point.addPointHistory(this);
    }
}
