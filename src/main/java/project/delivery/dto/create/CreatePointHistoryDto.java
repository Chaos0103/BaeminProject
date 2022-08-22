package project.delivery.dto.create;

import lombok.Data;
import project.delivery.domain.PointType;

@Data
public class CreatePointHistoryDto {

    private int pointValue;
    private String content;
    private PointType type;

    public CreatePointHistoryDto(int pointValue, String content, PointType type) {
        this.pointValue = pointValue;
        this.content = content;
        this.type = type;
    }
}
