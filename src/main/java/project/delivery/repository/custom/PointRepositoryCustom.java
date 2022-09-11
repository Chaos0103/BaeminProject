package project.delivery.repository.custom;

import project.delivery.domain.PointType;
import project.delivery.dto.PointHistoryDto;

import java.time.LocalDateTime;
import java.util.List;

public interface PointRepositoryCustom {

    /**
     *
     * @param pointId 포인트 PK
     * @param type PointType
     * @param period 조회 기간
     * @return content, pointValue, type, createdDate
     */
    List<PointHistoryDto> findPointHistoryByPointId(Long pointId, PointType type, LocalDateTime period);
}
