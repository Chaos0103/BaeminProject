package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.point.Point;

@Data
public class PointDto {

    private Long id;
    private Integer totalPoint;
    private Integer removePoint;
    private Integer balance;

    public PointDto(Point point) {
        this.id = point.getId();
        this.totalPoint = point.getTotalPoint();
        this.removePoint = point.getRemovePoint();
        this.balance = point.getBalance();
    }
}
