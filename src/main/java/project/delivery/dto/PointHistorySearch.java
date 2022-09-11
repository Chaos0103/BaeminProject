package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.point.PointType;

@Data
public class PointHistorySearch {

    private PointType type;
    private int month = 1;
}
