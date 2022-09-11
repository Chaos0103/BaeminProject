package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.point.PointType;

import java.time.LocalDateTime;

@Data
public class PointHistoryDto {

    private String content;
    private Integer pointValue;
    private PointType type;
    private LocalDateTime createdDate;
}
