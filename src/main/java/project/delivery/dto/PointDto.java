package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.Point;

@Data
public class PointDto {

    private Long id;
    private MemberDto memberDto;
    private int totalPoint;
    private int removePoint;
    private int balance;

    public PointDto(Point point) {
        this.id = point.getId();
        this.memberDto = new MemberDto(point.getMember());
        this.totalPoint = point.getTotalPoint();
        this.removePoint = point.getRemovePoint();
        this.balance = point.getBalance();
    }
}
