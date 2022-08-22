package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.PointHistory;
import project.delivery.domain.PointType;

import java.time.LocalDateTime;

@Data
public class PointHistoryDto {

    private Long id;
    private int pointValue;
    private String content;
    private PointType type;
    private LocalDateTime createdDate;

    public PointHistoryDto(PointHistory pointHistory) {
        this.id = pointHistory.getId();
        this.pointValue = pointHistory.getPointValue();
        this.content = pointHistory.getContent();
        this.type = pointHistory.getType();
        this.createdDate = pointHistory.getCreatedDate();
    }
}
